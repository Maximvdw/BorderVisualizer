/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 30/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package vdw.maxim.bordervisualizer.visualizer;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import vdw.maxim.bordervisualizer.BorderVisualizer;
import vdw.maxim.bordervisualizer.configuration.Config;
import vdw.maxim.bordervisualizer.datastore.SaveData;
import vdw.maxim.bordervisualizer.userinterface.SendConsole;


public class Visualize_Chunk {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Visualize_Chunk(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	public void visualize(Player player, Boolean allowMove ,String viewName,int viewType, String displayName) {
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
		data.save2DSquare(player, allowMove, viewName, viewType, x, z, size, null);
	}
}
