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
import vdw.maxim.bordervisualizer.locale.Messages;
import vdw.maxim.bordervisualizer.userinterface.SendConsole;
import vdw.maxim.bordervisualizer.userinterface.SendGame;

import com.tommytony.war.Warzone;
import com.tommytony.war.volume.ZoneVolume;


public class Visualize_War_Zone {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Visualize_War_Zone(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	public void visualize(Player player, Boolean allowMove , String viewName, int viewType, String displayName) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: WAR_ZONE");
			SendConsole.info("Starting VisualizePlayer:" + player.getName());
		}
		// Save the player's location
		Location location = player.getLocation();

		Warzone warZone = null;

		if (displayName == null) {
			// Get from position
			warZone = Warzone.getZoneByLocation(location);
		} else {
			// Get from text
			warZone = Warzone.getZoneByName(displayName);
		}

		// Check if region exists
		if (warZone != null) {
			ZoneVolume zoneVolume = warZone.getVolume();
			int x = (int) zoneVolume.getMinX();
			int y = (int) zoneVolume.getMinY();
			int z = (int) zoneVolume.getMinZ();
			int x_size = (int) zoneVolume.getMaxX() - x;
			int y_size = (int) zoneVolume.getMaxY() - y;
			int z_size = (int) zoneVolume.getMaxZ() - z;

			int[] min = new int[3];
			int[] max = new int[3];
			min[0] = x;
			min[1] = y;
			min[2] = z;
			max[0] = x+x_size;
			max[1] = y+y_size;
			max[2] = z+z_size;

			SaveData data = new SaveData(plugin);
			data.save3DCuboid(player, allowMove, viewName, viewType, min, max, null);
		} else {
			// Send message
			SendGame.sendMessage(
					Messages.error_nolocation.replace("{VIEW}", "WarZone"),
					player);
		}
	}
}
