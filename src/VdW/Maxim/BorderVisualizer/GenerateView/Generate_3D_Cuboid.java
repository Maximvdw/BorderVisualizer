/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 31/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer.GenerateView;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import VdW.Maxim.BorderVisualizer.BorderVisualizer;

public class Generate_3D_Cuboid {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Generate_3D_Cuboid(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	public void generate(Player player, int x, int y, int z, int x_size,
			int y_size, int z_size, Material block, boolean[] ignore) {
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
		for (i = 0; i <= x_size; i++) {
			for (j = 0; j <= y_size; j++) {
				corner1 = new Location(world, i + x, j + y, z);
				corner2 = new Location(world, i + x, j + y, z + z_size);
				if (corner1.getBlock().getType() == Material.AIR)
					player.sendBlockChange(corner1, block, (byte) 0);
				if (corner2.getBlock().getType() == Material.AIR)
					player.sendBlockChange(corner2, block, (byte) 0);
			}
		}
		for (j = 0; j <= y_size; j++) {
			for (k = 0; k <= z_size; k++) {
				corner3 = new Location(world, x, j + y, z + k);
				corner4 = new Location(world, x_size + x, j + y, z + k);
				if (corner3.getBlock().getType() == Material.AIR)
					player.sendBlockChange(corner3, block, (byte) 0);
				if (corner4.getBlock().getType() == Material.AIR)
					player.sendBlockChange(corner4, block, (byte) 0);
			}
		}
		for (i = 0; i <= x_size; i++) {
			for (k = 0; k <= z_size; k++) {
				corner5 = new Location(world, x + i, y, z + k);
				corner6 = new Location(world, x + i, y + y_size, z + k);
				if (corner5.getBlock().getType() == Material.AIR)
					player.sendBlockChange(corner5, block, (byte) 0);
				if (corner6.getBlock().getType() == Material.AIR)
					player.sendBlockChange(corner6, block, (byte) 0);
			}
		}
	}
}
