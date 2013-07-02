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
import vdw.maxim.bordervisualizer.locale.Messages;
import vdw.maxim.bordervisualizer.userinterface.SendConsole;
import vdw.maxim.bordervisualizer.userinterface.SendGame;

import com.massivecraft.factions.entity.BoardColls;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.mcore.ps.PS;

public class Visualize_Factions_FactionBlock {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Visualize_Factions_FactionBlock(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	public void visualize(Player player, Boolean allowMove, String viewName,
			int viewType, String displayName) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: Factions_FactionBLOCK");
			SendConsole.info("Starting VisualizePlayer:" + player.getName());
		}

		// Save the player's location
		Location location = player.getLocation();

		// Get the faction
		Faction f = null;

		// Get the faction
		f = BoardColls.get().getFactionAt(PS.valueOf(location));

		// Check if a faction has been found
		if (!f.isNone()) {
			// Show chunk
			// Get the coords of the chunk
			int size = 16; // Fixed value

			// Get the border of the object
			Chunk chunk = location.getChunk();
			int x = chunk.getX() * size;
			int z = chunk.getZ() * size;

			// Save data
			SaveData data = new SaveData(plugin);
			data.save2DSquare(player, allowMove, viewName, viewType, x, z,
					size, null);
		} else {
			// Send message
			SendGame.sendMessage(
					Messages.error_nolocation.replace("{VIEW}", "faction"),
					player);
			return; // No faction
		}
	}
}
