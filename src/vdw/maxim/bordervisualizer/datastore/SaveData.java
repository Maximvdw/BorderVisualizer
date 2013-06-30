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
import vdw.maxim.bordervisualizer.configuration.Config;
import vdw.maxim.bordervisualizer.generateview.View2DSquare;
import vdw.maxim.bordervisualizer.generateview.ViewObjects;
import vdw.maxim.bordervisualizer.userinterface.SendConsole;


public class SaveData {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public SaveData(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	// Save information about a 2D Square
	public void save2DSquare(Player player, Boolean allowMove, String view,
			int viewType, int x, int z, int size, boolean[] ignore) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("Saving 2D Square");
			SendConsole.info("Player: " + player.getName());
		}

		// Check if Data does not contain the player name
		// Add all data
		if (!dataPlayers.contains(player)) {
			// Add all data
			dataPlayers.addData(player);
			dataViews.addData(view);
			dataViewTypes.addData(viewType);
			dataViewObjects.addData(ViewObjects._2D_SQUARE);
			data2DSquare.addData(x, z, size, ignore);
			dataLocation.addData(player.getLocation());
			dataAllowMove.addData(allowMove);
			dataGlassified.addData(null);
		}
	}

	// Save information about a 2D Square Set
	public void save2DSquareSet(Player player, Boolean allowMove, String view,
			int viewType, View2DSquare[] square) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("Saving 2D SquareSet");
			SendConsole.info("Player: " + player.getName());
		}

		// Check if Data does not contain the player name
		// Add all data
		if (!dataPlayers.contains(player)) {
			// Add all data
			dataPlayers.addData(player);
			dataViews.addData(view);
			dataViewTypes.addData(viewType);
			dataViewObjects.addData(ViewObjects._2D_SQUARESET);
			data2DSquareSet.addData(square);
			dataLocation.addData(player.getLocation());
			dataAllowMove.addData(allowMove);
			dataGlassified.addData(null);
		}
	}

	// Save informatin about a 3D Cuboid
	public void save3DCuboid(Player player, Boolean allowMove, String view,
			int viewType, int[] min, int[] max, boolean[] ignore) {
		// Check if Data does not contain the player name
		if (!dataPlayers.contains(player)) {
			// Add all data
			dataPlayers.addData(player);
			dataLocation.addData(player.getLocation());
			dataViews.addData(view);
			dataViewTypes.addData(viewType);
			dataViewObjects.addData(ViewObjects._3D_CUBOID);
			data3DCuboid.addData(min, max);
			dataAllowMove.addData(allowMove);
			dataGlassified.addData(null);
		}
	}

	// Save informatin about a 2D Rectangle
	public void save2DRectangle(Player player, Boolean allowMove, String view,
			int viewType, int[] min, int[] max, boolean[] ignore) {
		// Check if Data does not contain the player name
		if (!dataPlayers.contains(player)) {
			// Add all data
			dataPlayers.addData(player);
			dataLocation.addData(player.getLocation());
			dataViews.addData(view);
			dataViewTypes.addData(viewType);
			dataViewObjects.addData(ViewObjects._2D_RECTANGLE);
			data2DRectangle.addData(min, max);
			dataAllowMove.addData(allowMove);
			dataGlassified.addData(null);
		}
	}
}
