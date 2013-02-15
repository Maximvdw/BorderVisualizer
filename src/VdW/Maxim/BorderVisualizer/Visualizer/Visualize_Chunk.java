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
import VdW.Maxim.BorderVisualizer.DataStore.SaveData;
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

	public void visualize(Player player,String viewName,int viewType, String displayName) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: CHUNK");
			SendConsole.info("Starting VisualizePlayer:" + player.getName());
		}
		
		// Save the player's location
		Location location = player.getLocation();

		// Get the border of the object
		Chunk chunk = location.getChunk();
		// Get the coords of the chunk
		int size = 16; // Fixed value
		
		int x = chunk.getX() * size;
		int z = chunk.getZ() * size;

		// Save data
		SaveData data = new SaveData(plugin);
		data.save2DSquare(player, viewName, viewType, x, z, size, null);
	}
}
