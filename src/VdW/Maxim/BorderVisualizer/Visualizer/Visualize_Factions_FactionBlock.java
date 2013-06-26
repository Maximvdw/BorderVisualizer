/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 30/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer.Visualizer;

import org.bukkit.entity.Player;


import VdW.Maxim.BorderVisualizer.BorderVisualizer;
import VdW.Maxim.BorderVisualizer.Configuration.Config;
import VdW.Maxim.BorderVisualizer.UserInterface.SendConsole;


public class Visualize_Factions_FactionBlock {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Visualize_Factions_FactionBlock(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	public void visualize(Player player, String viewName, int viewType,
			String displayName) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: Factions_FactionBLOCK");
			SendConsole.info("Starting VisualizePlayer:" + player.getName());
		}

	}
}
