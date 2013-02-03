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

public class Generate_2D_Square {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Generate_2D_Square(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	/* Generate Walls of Matterials */
	public void generate(Player player, int x,int y, int z, int size,
			int height, Material block, boolean[] ignore) {
		// Decrease size with one
		size -= 1;

		// Check what sides to ignore
		if (ignore == null) {
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
		int i = 0;
		int j = 0;
		for (i = y; i < height; i++) {
			for (j = 0; j < size-1; j++) {
				// Get all the corners
				corner1 = world.getBlockAt(1+x + j, i, z).getLocation();
				corner2 = world.getBlockAt(x + size, i, z + j+1).getLocation();
				corner3 = world.getBlockAt(x + j+1, i, z + size)
						.getLocation();
				corner4 = world.getBlockAt(x, i, z + j+1).getLocation();
				// Send the fake blocks
				if (corner1.getBlock().getType() == Material.AIR
						&& ignore[3] == false)
					player.sendBlockChange(corner1, block, (byte) 0);
				if (corner2.getBlock().getType() == Material.AIR
						&& ignore[0] == false)
					player.sendBlockChange(corner2, block, (byte) 0);
				if (corner3.getBlock().getType() == Material.AIR
						&& ignore[1] == false)
					player.sendBlockChange(corner3, block, (byte) 0);
				if (corner4.getBlock().getType() == Material.AIR
						&& ignore[2] == false)
					player.sendBlockChange(corner4, block, (byte) 0);
			}
			// Fill corners
			// Get all the corners
			corner1 = world.getBlockAt(x, i, z).getLocation();
			corner2 = world.getBlockAt(x + size, i, z).getLocation();
			corner3 = world.getBlockAt(x, i, z + size)
					.getLocation();
			corner4 = world.getBlockAt(x+ size, i, z+ size).getLocation();
			// Send the fake blocks
			if (corner1.getBlock().getType() == Material.AIR)
				player.sendBlockChange(corner1, block, (byte) 0);
			if (corner2.getBlock().getType() == Material.AIR)
				player.sendBlockChange(corner2, block, (byte) 0);
			if (corner3.getBlock().getType() == Material.AIR)
				player.sendBlockChange(corner3, block, (byte) 0);
			if (corner4.getBlock().getType() == Material.AIR)
				player.sendBlockChange(corner4, block, (byte) 0);
		}
	}
}