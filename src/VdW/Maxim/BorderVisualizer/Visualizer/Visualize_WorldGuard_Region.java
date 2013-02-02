/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 30/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer.Visualizer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import VdW.Maxim.BorderVisualizer.BorderVisualizer;
import VdW.Maxim.BorderVisualizer.Configuration.Config;
import VdW.Maxim.BorderVisualizer.Locale.Messages;
import VdW.Maxim.BorderVisualizer.UserInterface.SendConsole;
import VdW.Maxim.BorderVisualizer.UserInterface.SendGame;

public class Visualize_WorldGuard_Region {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Visualize_WorldGuard_Region(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	public void visualize_player(final Player player, int blockid,final String regionName) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: WORLDGUARD_REGION");
			SendConsole.info("Starting VisualizePlayer:" + player.getName()
					+ ";" + blockid);
		}
		// Run the render on a diffrent thread
		plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			public void run()
			{
				// Save the player's location
				Location location = player.getLocation();
				BorderVisualizer.bv_players_worldguard_region.add(player);
				BorderVisualizer.bv_regionName_worldguard_region.add(regionName);
				BorderVisualizer.bv_locations_worldguard_region.add(location);

				// Get World
				World world = location.getWorld();

				// Get the region
				WorldGuardPlugin wg = BorderVisualizer.WorldGuard;
				RegionManager rm = wg.getRegionManager(world);
				ProtectedRegion region = rm.getRegion(regionName);
				String id = region.getId();
				String tn = region.getTypeName();
				BlockVector l0 = region.getMinimumPoint();
				BlockVector l1 = region.getMaximumPoint();

				double x_loc = l0.getX();
				double y_loc = l0.getY();
				double z_loc = l0.getZ();
				double x_len = l1.getX() - l0.getX();
				double y_len = l1.getY() - l0.getY();
				double z_len = l1.getZ() - l0.getZ();

				/* DEBUG LOGGING */
				if (Config.debugMode == true) {
					SendConsole.info("Visualization data: RegionID=" + id);
					SendConsole.info("Visualization data: MaxPoint=" + l1);
					SendConsole.info("Visualization data: MinPoint=" + l0);
					SendConsole.info("Visualization data: Itteration=" + x_len * y_len
							* z_len);
				}

				if (tn.equalsIgnoreCase("cuboid")) { /* Cubiod region? */
					/* Make outline */
					// Define the four corners
					Location corner1;
					Location corner2;
					Location corner3;
					Location corner4;
					Location corner5;
					Location corner6;

					double i = 0;
					double j = 0;
					double k = 0;
					for (i = 0; i <= x_len; i++) {
						for (j = 0; j <= y_len; j++) {
							corner1 = new Location(world, i + x_loc, j + y_loc, z_loc);
							corner2 = new Location(world, i + x_loc, j + y_loc, z_loc
									+ z_len);
							if (corner1.getBlock().getType() == Material.AIR)
								player.sendBlockChange(corner1, Material.GLASS,
										(byte) 0);
							if (corner2.getBlock().getType() == Material.AIR)
								player.sendBlockChange(corner2, Material.GLASS,
										(byte) 0);
						}
					}
					for (j = 0; j <= y_len; j++) {
						for (k = 0; k <= z_len; k++) {
							corner3 = new Location(world, x_loc, j + y_loc, z_loc + k);
							corner4 = new Location(world, x_len + x_loc, j + y_loc,
									z_loc + k);
							if (corner3.getBlock().getType() == Material.AIR)
								player.sendBlockChange(corner3, Material.GLASS,
										(byte) 0);
							if (corner4.getBlock().getType() == Material.AIR)
								player.sendBlockChange(corner4, Material.GLASS,
										(byte) 0);
						}
					}
					for (i = 0; i <= x_len; i++) {
						for (k = 0; k <= z_len; k++) {
							corner5 = new Location(world, x_loc + i, y_loc, z_loc + k);
							corner6 = new Location(world, x_loc + i, y_loc + y_len,
									z_loc + k);
							if (corner5.getBlock().getType() == Material.AIR)
								player.sendBlockChange(corner5, Material.GLASS,
										(byte) 0);
							if (corner6.getBlock().getType() == Material.AIR)
								player.sendBlockChange(corner6, Material.GLASS,
										(byte) 0);
						}
					}
				} else {
					// Not supported
					SendGame.sendMessage(Messages.error_wg_notsupported, player);
				}

			}
		});
	}

	public void remove_player(final Player player, final String nextView, int blockid) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: WORLDGUARD_REGION");
			SendConsole.info("Starting RemovePlayer:" + player.getName());
		}
		// Run the render on a diffrent thread
		plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			public void run()
			{
				// Get the players location before executing the command
				int index = BorderVisualizer.bv_players_worldguard_region
						.indexOf(player);
				String regionName = BorderVisualizer.bv_regionName_worldguard_region
						.get(index);
				Location location = BorderVisualizer.bv_locations_worldguard_region
						.get(index);

				// Check if the player asked for 'A new region visualization'
				if ((nextView != null) && (regionName != nextView)) {
					SendGame.sendMessage(Messages.warning_nextview.replace("{VIEW}",
							"WorldGuard Region"), player);
				}

				// Remove the saved data
				BorderVisualizer.bv_players_worldguard_region.remove(player);
				BorderVisualizer.bv_regionName_worldguard_region.remove(index);
				BorderVisualizer.bv_locations_worldguard_region.remove(index);

				// Get World
				World world = location.getWorld();

				// Get the region
				WorldGuardPlugin wg = BorderVisualizer.WorldGuard;
				RegionManager rm = wg.getRegionManager(world);
				ProtectedRegion region = rm.getRegion(regionName);
				String id = region.getId();
				String tn = region.getTypeName();
				BlockVector l0 = region.getMinimumPoint();
				BlockVector l1 = region.getMaximumPoint();

				double x_loc = l0.getX();
				double y_loc = l0.getY();
				double z_loc = l0.getZ();
				double x_len = l1.getX() - l0.getX();
				double y_len = l1.getY() - l0.getY();
				double z_len = l1.getZ() - l0.getZ();

				/* DEBUG LOGGING */
				if (Config.debugMode == true) {
					SendConsole.info("Visualization data: RegionID=" + id);
					SendConsole.info("Visualization data: MaxPoint=" + l1);
					SendConsole.info("Visualization data: MinPoint=" + l0);
					SendConsole.info("Visualization data: Itteration=" + x_len * y_len
							* z_len);
				}

				if (tn.equalsIgnoreCase("cuboid")) { /* Cubiod region? */
					/* Make outline */
					// Define the four corners
					Location corner1;
					Location corner2;
					Location corner3;
					Location corner4;
					Location corner5;
					Location corner6;

					double i = 0;
					double j = 0;
					double k = 0;
					for (i = 0; i <= x_len; i++) {
						for (j = 0; j <= y_len; j++) {
							corner1 = new Location(world, i + x_loc, j + y_loc, z_loc);
							corner2 = new Location(world, i + x_loc, j + y_loc, z_loc
									+ z_len);
							if (corner1.getBlock().getType() == Material.AIR)
								player.sendBlockChange(corner1, Material.AIR, (byte) 0);
							if (corner2.getBlock().getType() == Material.AIR)
								player.sendBlockChange(corner2, Material.AIR, (byte) 0);
						}
					}
					for (j = 0; j <= y_len; j++) {
						for (k = 0; k <= z_len; k++) {
							corner3 = new Location(world, x_loc, j + y_loc, z_loc + k);
							corner4 = new Location(world, x_len + x_loc, j + y_loc,
									z_loc + k);
							if (corner3.getBlock().getType() == Material.AIR)
								player.sendBlockChange(corner3, Material.AIR, (byte) 0);
							if (corner4.getBlock().getType() == Material.AIR)
								player.sendBlockChange(corner4, Material.AIR, (byte) 0);
						}
					}
					for (i = 0; i <= x_len; i++) {
						for (k = 0; k <= z_len; k++) {
							corner5 = new Location(world, x_loc + i, y_loc, z_loc + k);
							corner6 = new Location(world, x_loc + i, y_loc + y_len,
									z_loc + k);
							if (corner5.getBlock().getType() == Material.AIR)
								player.sendBlockChange(corner5, Material.AIR, (byte) 0);
							if (corner6.getBlock().getType() == Material.AIR)
								player.sendBlockChange(corner6, Material.AIR, (byte) 0);
						}
					}
				} else {
					// Not supported
					SendGame.sendMessage(Messages.error_wg_notsupported, player);
				}
			}
		});
	}
}
