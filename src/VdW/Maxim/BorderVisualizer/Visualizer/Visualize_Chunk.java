/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 30/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer.Visualizer;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import VdW.Maxim.BorderVisualizer.BorderVisualizer;
import VdW.Maxim.BorderVisualizer.Configuration.Config;
import VdW.Maxim.BorderVisualizer.UserInterface.SendConsole;

public class Visualize_Chunk {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Visualize_Chunk(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	public void visualize_player(Player player, int blockid) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: CHUNK");
			SendConsole.info("Starting VisualizePlayer:" + player.getName()
					+ ";" + blockid);
		}

		// Save the player's location
		Location location = player.getLocation();
		BorderVisualizer.bv_players_chunk.add(player);
		BorderVisualizer.bv_locations_chunk.add(player.getLocation());

		// Get the border of the object
		Chunk chunk = location.getChunk();
		// Get the four corners of the chunk
		Location corner1 = chunk.getBlock(0, 0, 0).getLocation();
		Location corner2 = chunk.getBlock(15, 0, 0).getLocation();
		Location corner3 = chunk.getBlock(0, 0, 15).getLocation();
		Location corner4 = chunk.getBlock(15, 0, 15).getLocation();
		int i = 0;
		int j = 0;
		for (i = 0; i < 127; i++)
			for (j = 0; j < 15; j++) {
				corner1 = chunk.getBlock(j, i, 0).getLocation();
				corner2 = chunk.getBlock(15, i, j).getLocation();
				corner3 = chunk.getBlock(15 - j, i, 15).getLocation();
				corner4 = chunk.getBlock(0, i, 15 - j).getLocation();
				if (corner1.getBlock().getType() == Material.AIR)
					player.sendBlockChange(corner1, Material.GLASS, (byte) 0);
				if (corner2.getBlock().getType() == Material.AIR)
					player.sendBlockChange(corner2, Material.GLASS, (byte) 0);
				if (corner3.getBlock().getType() == Material.AIR)
					player.sendBlockChange(corner3, Material.GLASS, (byte) 0);
				if (corner4.getBlock().getType() == Material.AIR)
					player.sendBlockChange(corner4, Material.GLASS, (byte) 0);
			}
	}

	public void remove_player(Player player) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: CHUNK");
			SendConsole.info("Starting RemovePlayer:" + player.getName());
		}
		// Get the players location before executing the command
		int index = BorderVisualizer.bv_players_chunk.indexOf(player);
		Location location = (Location) BorderVisualizer.bv_locations_chunk
				.get(index);

		// Remove the saved data
		BorderVisualizer.bv_players_chunk.remove(player);
		BorderVisualizer.bv_locations_chunk.remove(index);

		// Get the border of the object
		Chunk chunk = location.getChunk();
		// Get the four corners of the chunk
		Location corner1 = chunk.getBlock(0, 0, 0).getLocation();
		Location corner2 = chunk.getBlock(15, 0, 0).getLocation();
		Location corner3 = chunk.getBlock(0, 0, 15).getLocation();
		Location corner4 = chunk.getBlock(15, 0, 15).getLocation();
		int i = 0;
		int j = 0;
		for (i = 0; i < 127; i++)
			for (j = 0; j < 15; j++) {
				corner1 = chunk.getBlock(j, i, 0).getLocation();
				corner2 = chunk.getBlock(15, i, j).getLocation();
				corner3 = chunk.getBlock(15 - j, i, 15).getLocation();
				corner4 = chunk.getBlock(0, i, 15 - j).getLocation();
				if (corner1.getBlock().getType() == Material.AIR)
					player.sendBlockChange(corner1, Material.AIR, (byte) 0);
				if (corner2.getBlock().getType() == Material.AIR)
					player.sendBlockChange(corner2, Material.AIR, (byte) 0);
				if (corner3.getBlock().getType() == Material.AIR)
					player.sendBlockChange(corner3, Material.AIR, (byte) 0);
				if (corner4.getBlock().getType() == Material.AIR)
					player.sendBlockChange(corner4, Material.AIR, (byte) 0);
			}
	}
}
