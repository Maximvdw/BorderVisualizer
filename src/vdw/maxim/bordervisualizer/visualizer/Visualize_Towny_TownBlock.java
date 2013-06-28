/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 30/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package vdw.maxim.bordervisualizer.visualizer;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import vdw.maxim.bordervisualizer.BorderVisualizer;
import vdw.maxim.bordervisualizer.configuration.Config;
import vdw.maxim.bordervisualizer.datastore.SaveData;
import vdw.maxim.bordervisualizer.generateview.View2DSquare;
import vdw.maxim.bordervisualizer.locale.Messages;
import vdw.maxim.bordervisualizer.userinterface.SendConsole;
import vdw.maxim.bordervisualizer.userinterface.SendGame;


import com.palmergames.bukkit.towny.object.Coord;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class Visualize_Towny_TownBlock {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Visualize_Towny_TownBlock(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	public void visualize(Player player, Boolean allowMove , String viewName, int viewType,
			String displayName) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: TOWNY_TOWNBLOCK");
			SendConsole.info("Starting VisualizePlayer:" + player.getName());
		}

		// Save the player's location
		Location location = player.getLocation();

		// Get the current townblock
		TownBlock townBlock = TownyUniverse.getTownBlock(location);
		// Check if townBlock exist
		if (townBlock != null) {
			// Get the size of a townblock
			int size = Coord.getCellSize();
			// Get location of the townblock
			int x = size * townBlock.getX();
			int z = size * townBlock.getZ();

			/* DEBUG LOGGING */
			if (Config.debugMode == true) {
				SendConsole.info("Visualization data: TownBlockSize=" + size);
				SendConsole.info("Visualization data: Block_x=" + x);
				SendConsole.info("Visualization data: Block_z=" + z);
				SendConsole.info("Visualization data: TownBlock_x="
						+ townBlock.getX());
				SendConsole.info("Visualization data: TownBlock_z="
						+ townBlock.getZ());
			}
			View2DSquare townBlockView = new View2DSquare();
			townBlockView.addData(x, z, size, null);

			// Save the Square Block set
			SaveData data = new SaveData(plugin);
			data.save2DSquare(player, allowMove, viewName, viewType, x, z, size, null);
		} else {
			// Send message
			SendGame.sendMessage(
					Messages.error_nolocation.replace("{VIEW}", "townblock"),
					player);
		}
	}
}
