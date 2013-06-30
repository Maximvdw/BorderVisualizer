/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 31/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package vdw.maxim.bordervisualizer.generateview;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import vdw.maxim.bordervisualizer.BorderVisualizer;
import vdw.maxim.bordervisualizer.configuration.Config;
import vdw.maxim.bordervisualizer.datastore.dataGlassified;
import vdw.maxim.bordervisualizer.datastore.dataPlayers;
import vdw.maxim.bordervisualizer.userinterface.SendConsole;

public class Generate_3D_CuboidWand {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Generate_3D_CuboidWand(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	public void generate(final Player player, final int x, final int y,
			final int z, final int x_size, final int y_size, final int z_size,
			final Material block, boolean[] ignore) {
		final List<Location> glassified = new ArrayList<Location>();
		final List<Location> glassifiedPrev = (List<Location>) dataGlassified
				.getData(dataPlayers.getIndex(player));

		// Check what sides to ignore
		if (ignore == null) {
			ignore = new boolean[7];
			// Do not ignore any sides
			ignore[0] = false;
			ignore[1] = false;
			ignore[2] = false;
			ignore[3] = false;
			ignore[4] = false;
			ignore[5] = false;
			ignore[6] = false;
		}

		// Check distance
		int x_distance = Math.abs((int) player.getLocation().getX() - x);
		int z_distance = Math.abs((int) player.getLocation().getZ() - z);

		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("Distance X: " + x_distance);
			SendConsole.info("Distance Z: " + z_distance);
		}

		// Get the world of the player
		World world = player.getWorld();

		// Define the six corners
		Location corner1;
		Location corner2;
		Location corner3;
		Location corner4;
		Location corner5;
		Location corner6;

		// Player location
		Location plLoc = player.getLocation();
		if (glassifiedPrev != null) {
			for (Location bLoc : glassifiedPrev) {
				if (plLoc.distance(bLoc) >= 5) {
					player.sendBlockChange(bLoc, Material.AIR, (byte) 0);
				} else {
					glassified.add(bLoc);
				}
			}
		} else {
			/* DEBUG LOGGING */
			if (Config.debugMode == true) {
				SendConsole.info("WAND: No previous blocks to delete");
			}
		}

		double i = 0;
		double j = 0;
		double k = 0;
		for (i = 0; i <= x_size; i++) {
			for (j = 0; j <= y_size; j++) {
				corner1 = new Location(world, i + x, j + y, z);
				corner2 = new Location(world, i + x, j + y, z + z_size);
				if (plLoc.distance(corner1) < 5) {
					if (corner1.getBlock().getType() == Material.AIR) {
						player.sendBlockChange(corner1, block, (byte) 0);
						glassified.add(corner1);
					}
				}
				if (plLoc.distance(corner2) < 5) {
					if (corner2.getBlock().getType() == Material.AIR) {
						player.sendBlockChange(corner2, block, (byte) 0);
						glassified.add(corner2);
					}
				}
			}
		}
		for (j = 0; j <= y_size; j++) {
			for (k = 0; k <= z_size; k++) {
				corner3 = new Location(world, x, j + y, z + k);
				corner4 = new Location(world, x_size + x, j + y, z + k);
				if (plLoc.distance(corner3) < 5) {
					if (corner3.getBlock().getType() == Material.AIR) {
						player.sendBlockChange(corner3, block, (byte) 0);
						glassified.add(corner3);
					}
				}
				if (plLoc.distance(corner4) < 5) {
					if (corner4.getBlock().getType() == Material.AIR) {
						player.sendBlockChange(corner4, block, (byte) 0);
						glassified.add(corner4);
					}
				}
			}
		}
		for (i = 0; i <= x_size; i++) {
			for (k = 0; k <= z_size; k++) {
				corner5 = new Location(world, x + i, y, z + k);
				corner6 = new Location(world, x + i, y + y_size, z + k);
				if (plLoc.distance(corner5) < 5) {
					if (corner5.getBlock().getType() == Material.AIR) {
						player.sendBlockChange(corner5, block, (byte) 0);
						glassified.add(corner5);
					}
				}
				if (plLoc.distance(corner6) < 5) {
					if (corner6.getBlock().getType() == Material.AIR) {
						player.sendBlockChange(corner6, block, (byte) 0);
						glassified.add(corner6);
					}
				}
			}
		}
		
		// Save glassified
		dataGlassified.data.set(dataPlayers.getIndex(player), glassified);
	}
}
