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
import VdW.Maxim.BorderVisualizer.GenerateView.Generate_2D_Square;
import VdW.Maxim.BorderVisualizer.Locale.Messages;
import VdW.Maxim.BorderVisualizer.UserInterface.SendConsole;
import VdW.Maxim.BorderVisualizer.UserInterface.SendGame;

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
		// Get the coords of the chunk
		int size = 16; // Fixed value
		
		// Get the size and y position to show blocks
		int height = (int)location.getY() + 20;
		int y = 0;
		
		int x = chunk.getX() * size;
		int z = chunk.getZ() * size;
		Material block = Material.GLASS; // Block to replace it with

		// Generate the 2D Walls
		Generate_2D_Square generator = new Generate_2D_Square(plugin);
		generator.generate(player, x,y, z, size, height, block, null);

		// Send Message
		SendGame.sendMessage(
				Messages.config_visualized.replace("{VIEW}", "Chunk"), player);
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
		// Get the coords of the chunk
		int size = 16; // Fixed value
		
		// Get the size and y position to show blocks
		int height = (int)location.getY() + 20;
		int y = 0;
		
		int x = chunk.getX() * size;
		int z = chunk.getZ() * size;
		Material block = Material.AIR; // Block to replace it with

		// Generate the 2D Walls
		Generate_2D_Square generator = new Generate_2D_Square(plugin);
		generator.generate(player, x,y, z, size, height, block, null);
	}
}
