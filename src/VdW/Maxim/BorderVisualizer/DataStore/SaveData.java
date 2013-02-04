/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 31/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer.DataStore;

import org.bukkit.entity.Player;

import VdW.Maxim.BorderVisualizer.BorderVisualizer;

public class SaveData {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public SaveData(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	public void save(Player player, String view, int viewType, int[] x,
			int[] y, int[] z, int size, String nameArg) {
		// Start detecting what type of view it is
		
	}
}
