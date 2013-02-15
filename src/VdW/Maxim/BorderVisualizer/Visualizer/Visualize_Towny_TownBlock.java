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
import org.bukkit.entity.Player;

import VdW.Maxim.BorderVisualizer.BorderVisualizer;
import VdW.Maxim.BorderVisualizer.Configuration.Config;
import VdW.Maxim.BorderVisualizer.GenerateView.Generate_2D_Square;
import VdW.Maxim.BorderVisualizer.Locale.Messages;
import VdW.Maxim.BorderVisualizer.UserInterface.SendConsole;
import VdW.Maxim.BorderVisualizer.UserInterface.SendGame;

import com.palmergames.bukkit.towny.object.Coord;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class Visualize_Towny_TownBlock {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Visualize_Towny_TownBlock(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	public void visualize(Player player, int blockid) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: TOWNY_TOWNBLOCK");
			SendConsole.info("Starting VisualizePlayer:" + player.getName()
					+ ";" + blockid);
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

		} else {
			// Send message
			SendGame.sendMessage(
					Messages.error_nolocation.replace("{VIEW}", "townblock"), player);
		}
	}

}
