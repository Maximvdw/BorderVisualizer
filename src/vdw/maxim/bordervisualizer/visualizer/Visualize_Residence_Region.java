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

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import com.bekvon.bukkit.residence.protection.ResidenceManager;

import vdw.maxim.bordervisualizer.BorderVisualizer;
import vdw.maxim.bordervisualizer.configuration.Config;
import vdw.maxim.bordervisualizer.datastore.SaveData;
import vdw.maxim.bordervisualizer.locale.Messages;
import vdw.maxim.bordervisualizer.userinterface.SendConsole;
import vdw.maxim.bordervisualizer.userinterface.SendGame;

public class Visualize_Residence_Region {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Visualize_Residence_Region(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	public void visualize(Player player, Boolean allowMove, String viewName,
			int viewType, String displayName) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: RESIDENCE_REGION");
			SendConsole.info("Starting VisualizePlayer:" + player.getName());
		}
		// Save the player's location
		Location location = player.getLocation();

		// Get the region
		String regionName = displayName;
		@SuppressWarnings("static-access")
		ResidenceManager resm = BorderVisualizer.Residence
				.getResidenceManager();
		ClaimedResidence claim = null;
		if (regionName == null) {
			// Get from position
			claim = resm.getByLoc(location);
		} else {
			// Get from text
			claim = resm.getByName(regionName);
		}

		// Check if region exists
		if (claim != null) {
			CuboidArea[] areas = claim.getAreaArray();
			CuboidArea area = areas[0];
			int x = (int) area.getLowLoc().getX();
			int y = (int) area.getLowLoc().getY();
			int z = (int) area.getLowLoc().getZ();
			int x_size = (int) (area.getHighLoc().getX()) - (int) x;
			int y_size = (int) (area.getHighLoc().getY()) - (int) y;
			int z_size = (int) (area.getHighLoc().getZ()) - (int) z;

			int[] min = new int[3];
			int[] max = new int[3];
			min[0] = x;
			min[1] = y;
			min[2] = z;
			max[0] = x + x_size;
			max[1] = y + y_size;
			max[2] = z + z_size;

			// Generate the 3D cuboid
			// Save data
			SaveData data = new SaveData(plugin);
			data.save3DCuboid(player, allowMove, viewName, viewType, min, max,
					null);
		} else {
			// Send message
			SendGame.sendMessage(
					Messages.error_nolocation.replace("{VIEW}", "Residence"),
					player);
		}
	}
}
