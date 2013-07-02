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
import vdw.maxim.bordervisualizer.userinterface.SendGame;

public class Generate_2D_SquareWand {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Generate_2D_SquareWand(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	/* Generate Walls of Matterials */
	public void generate(final Player player, final int x, final int y,
			final int z, int size_sides, final int height,
			final Material block, final boolean[] ignore_sides) {
		final List<Location> glassified = new ArrayList<Location>();
		@SuppressWarnings("unchecked")
		final List<Location> glassifiedPrev = (List<Location>) dataGlassified
				.getData(dataPlayers.getIndex(player));
		// Decrease size with one
		final int size = size_sides - 1;

		// Check distance
		int x_distance = Math.abs((int) player.getLocation().getX() - x);
		int z_distance = Math.abs((int) player.getLocation().getZ() - z);

		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("GENERATE 2D SQUARE WAND");
			SendConsole.info("Distance X: " + x_distance);
			SendConsole.info("Distance Z: " + z_distance);
		}

		// Check what sides to ignore
		boolean[] ignore = ignore_sides;
		if (ignore_sides == null) {
			ignore = new boolean[4];
			// Do not ignore any sides
			ignore[0] = false;
			ignore[1] = false;
			ignore[2] = false;
			ignore[3] = false;
		}

		// Get the world of the player
		World world = player.getWorld();

		// Get the four corners of the chunk
		Location corner1;
		Location corner2;
		Location corner3;
		Location corner4;
		Location corner1_new;
		Location corner2_new;
		Location corner3_new;
		Location corner4_new;

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

		int i = 0;
		int j = 0;
		for (i = y; i < height; i++) {
			for (j = 0; j < size - 1; j++) {
				// Get all the corners
				corner1_new = new Location(world, 1 + x + j, i, z);
				corner2_new = new Location(world, x + size, i, z + j + 1);
				corner3_new = new Location(world, x + j + 1, i, z + size);
				corner4_new = new Location(world, x, i, z + j + 1);

				corner1 = world.getBlockAt(corner1_new).getLocation();
				corner2 = world.getBlockAt(corner2_new).getLocation();
				corner3 = world.getBlockAt(corner3_new).getLocation();
				corner4 = world.getBlockAt(corner4_new).getLocation();
				// Send the fake blocks
				if (plLoc.distance(corner1_new) < 5) {
					if (corner1.getBlock().getType() == Material.AIR
							&& ignore[3] == false) {
						player.sendBlockChange(corner1, block, (byte) 0);
						glassified.add(corner1);
					}
				}

				if (plLoc.distance(corner2_new) < 5) {
					if (corner2.getBlock().getType() == Material.AIR
							&& ignore[0] == false) {
						player.sendBlockChange(corner2, block, (byte) 0);
						glassified.add(corner2);
					}
				}
				if (plLoc.distance(corner3_new) < 5) {
					if (corner3.getBlock().getType() == Material.AIR
							&& ignore[1] == false) {
						player.sendBlockChange(corner3, block, (byte) 0);
						glassified.add(corner3);
					}
				}
				if (plLoc.distance(corner4_new) < 5) {
					if (corner4.getBlock().getType() == Material.AIR
							&& ignore[2] == false) {
						player.sendBlockChange(corner4, block, (byte) 0);
						glassified.add(corner4);
					}
				}
			}
			// Fill corners
			// Get all the corners
			corner1_new = new Location(world, x, i, z);
			corner2_new = new Location(world, x + size, i, z);
			corner3_new = new Location(world, x, i, z + size);
			corner4_new = new Location(world, x + size, i, z + size);
			corner1 = world.getBlockAt(corner1_new).getLocation();
			corner2 = world.getBlockAt(corner2_new).getLocation();
			corner3 = world.getBlockAt(corner3_new).getLocation();
			corner4 = world.getBlockAt(corner4_new).getLocation();
			// Send the fake blocks
			if (plLoc.distance(corner1_new) < 5) {
				if (corner1.getBlock().getType() == Material.AIR) {
					player.sendBlockChange(corner1, block, (byte) 0);
					glassified.add(corner1);
				}
			}

			if (plLoc.distance(corner2_new) < 5) {
				if (corner2.getBlock().getType() == Material.AIR) {
					player.sendBlockChange(corner2, block, (byte) 0);
					glassified.add(corner2);
				}
			}
			if (plLoc.distance(corner3_new) < 5) {
				if (corner3.getBlock().getType() == Material.AIR) {
					player.sendBlockChange(corner3, block, (byte) 0);
					glassified.add(corner3);
				}
			}
			if (plLoc.distance(corner4_new) < 5) {
				if (corner4.getBlock().getType() == Material.AIR) {
					player.sendBlockChange(corner4, block, (byte) 0);
					glassified.add(corner4);
				}
			}
		}
		// Save glassified
		dataGlassified.data.set(dataPlayers.getIndex(player), glassified);
	}
}
