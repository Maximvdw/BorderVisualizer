/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 31/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package vdw.maxim.bordervisualizer.generateview;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import vdw.maxim.bordervisualizer.BorderVisualizer;
import vdw.maxim.bordervisualizer.configuration.Config;
import vdw.maxim.bordervisualizer.locale.Messages;
import vdw.maxim.bordervisualizer.userinterface.SendConsole;
import vdw.maxim.bordervisualizer.userinterface.SendGame;


public class Generate_3D_CuboidFrame {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Generate_3D_CuboidFrame(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	public void generate(final Player player, final int x, final int y,
			final int z, final int x_size, final int y_size, final int z_size,
			final Material block, boolean[] ignore) {
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

		if (x_distance < 100+x_size || z_distance < 100+z_size){
			// Run the render on a diffrent thread
			plugin.getServer().getScheduler()
					.runTaskAsynchronously(plugin, new Runnable() {
						public void run() {
							// Get the world of the player
							World world = player.getWorld();

							// Define the six corners
							Location corner1;
							Location corner2;
							Location corner3;
							Location corner4;
							Location corner5;
							Location corner6;

							double i = 0;
							double j = 0;
							double k = 0;
							for (i = 0; i <= 1; i++) {
								i = i*x_size;
								for (j = 0; j <= y_size; j++) {
									corner1 = new Location(world, i + x, j + y,
											z);
									corner2 = new Location(world, i + x, j + y,
											z + z_size);
									if (corner1.getBlock().getType() == Material.AIR)
										player.sendBlockChange(corner1, block,
												(byte) 0);
									if (corner2.getBlock().getType() == Material.AIR)
										player.sendBlockChange(corner2, block,
												(byte) 0);
								}
							}
							for (j = 0; j <= 1; j++) {
								j = j*y_size;
								for (k = 0; k <= z_size; k++) {
									corner3 = new Location(world, x, j + y, z
											+ k);
									corner4 = new Location(world, x_size + x, j
											+ y, z + k);
									if (corner3.getBlock().getType() == Material.AIR)
										player.sendBlockChange(corner3, block,
												(byte) 0);
									if (corner4.getBlock().getType() == Material.AIR)
										player.sendBlockChange(corner4, block,
												(byte) 0);
								}
							}
							for (i = 0; i <= x_size; i++) {
								for (k = 0; k <= 1; k++) {
									k = k*z_size;
									corner5 = new Location(world, x + i, y, z
											+ k);
									corner6 = new Location(world, x + i, y
											+ y_size, z + k);
									if (corner5.getBlock().getType() == Material.AIR)
										player.sendBlockChange(corner5, block,
												(byte) 0);
									if (corner6.getBlock().getType() == Material.AIR)
										player.sendBlockChange(corner6, block,
												(byte) 0);
								}
							}
						}
					});
		}else{
			// Send message
			SendGame.sendMessage(Messages.error_view_distance, player);
		}
	}
}
