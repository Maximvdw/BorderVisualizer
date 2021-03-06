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
import vdw.maxim.bordervisualizer.userinterface.SendConsole;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.GriefPrevention;

public class Visualize_GriefPrevention_Claim {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Visualize_GriefPrevention_Claim(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	public void visualize(Player player, Boolean allowMove, String viewName, int viewType,
			String displayName) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: GriefPrevention_CLAIM");
			SendConsole.info("Starting VisualizePlayer:" + player.getName());
		}

		// Save the player's location
		Location location = player.getLocation();

		// Get the claim at the players location
		GriefPrevention gp = BorderVisualizer.GriefPrevention;
		DataStore ds = gp.dataStore;
		Claim cl = ds.getClaimAt(location, false, null);
		// Check if townBlock exist
		if (cl != null) {
			int[] min = new int[2];
			int[] max = new int[2];
			min[0] = (int) cl.getLesserBoundaryCorner().getX();
			min[1] = (int) cl.getLesserBoundaryCorner().getZ();
			max[0] = (int) cl.getGreaterBoundaryCorner().getX();
			max[1] = (int) cl.getGreaterBoundaryCorner().getZ();
			
			// Save 2D Rectangle data
			SaveData data = new SaveData(plugin);
			data.save2DRectangle(player, allowMove, viewName, viewType, min, max, null);
		}
	}
}
