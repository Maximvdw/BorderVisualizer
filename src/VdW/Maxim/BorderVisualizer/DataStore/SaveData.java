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
import VdW.Maxim.BorderVisualizer.GenerateView.ViewObjects;

public class SaveData {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public SaveData(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	// Save information about a 2D Square
	public void save2DSquare(Player player, String view, int viewType,int x,int z,int size)
	{
		// Check if Data does not contain the player name
		if (!dataPlayers.contains(player))
		{
			// Add all data
			dataPlayers.addData(player);
			dataViews.addData(view);
			dataViewTypes.addData(viewType);
			dataViewObjects.addData(ViewObjects._2D_SQUARE);
			data2DSquare.addData(x, z, size);
		}
	}
}
