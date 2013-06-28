/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 31/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package vdw.maxim.bordervisualizer.datastore;

import org.bukkit.entity.Player;

import vdw.maxim.bordervisualizer.BorderVisualizer;
import vdw.maxim.bordervisualizer.generateview.ViewObjects;


public class ResetData {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public ResetData(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	// Remove all saved data from a specific player
	public void resetAll(Player player)
	{
		int index = dataPlayers.getIndex(player);
		dataPlayers.removeData(index);
		dataLocation.removeData(index);
		dataViews.removeData(index);
		dataViewTypes.removeData(index);
		dataAllowMove.removeData(index);
		
		int viewObject = (int) dataViewObjects.getData(index);
		// Delete the data based on viewobject
		if (viewObject == ViewObjects._2D_SQUARE)
		{
			data2DSquare.removeData(index);
		}else if (viewObject == ViewObjects._2D_SQUARESET)
		{
			data2DSquareSet.removeData(index);
		}else if (viewObject == ViewObjects._3D_CUBOID)
		{
			data3DCuboid.removeData(index);
		}
		
		dataViewObjects.removeData(index); // Remove object
	}
}
