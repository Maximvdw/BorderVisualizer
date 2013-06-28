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
import org.bukkit.World;
import org.bukkit.entity.Player;

import vdw.maxim.bordervisualizer.BorderVisualizer;
import vdw.maxim.bordervisualizer.configuration.Config;
import vdw.maxim.bordervisualizer.datastore.SaveData;
import vdw.maxim.bordervisualizer.locale.Messages;
import vdw.maxim.bordervisualizer.userinterface.SendConsole;
import vdw.maxim.bordervisualizer.userinterface.SendGame;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;


public class Visualize_WorldGuard_Region {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Visualize_WorldGuard_Region(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	public void visualize(Player player, Boolean allowMove , String viewName, int viewType, String displayName) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: WORLDGUARD_REGION");
			SendConsole.info("Starting VisualizePlayer:" + player.getName());
		}
		// Save the player's location
		Location location = player.getLocation();

		// Get World
		World world = location.getWorld();

		// Get the region
		String regionName = displayName;
		WorldGuardPlugin wg = BorderVisualizer.WorldGuard;
		RegionManager rm = wg.getRegionManager(world);
		// Get the region
		ProtectedRegion region = null;

		if (regionName == null) {
			// Get from position
			ApplicableRegionSet rs = rm.getApplicableRegions(location);
			if (rs.iterator().hasNext()) {
				region = rs.iterator().next();
			}
		} else {
			// Get from text
			region = rm.getRegion(regionName);
		}

		// Check if region exists
		if (region != null) {
			String id = region.getId();
			String tn = region.getTypeName();
			BlockVector l0 = region.getMinimumPoint();
			BlockVector l1 = region.getMaximumPoint();

			int x = (int) l0.getX();
			int y = (int) l0.getY();
			int z = (int) l0.getZ();
			int x_size = (int) l1.getX() - (int) l0.getX();
			int y_size = (int) l1.getY() - (int) l0.getY();
			int z_size = (int) l1.getZ() - (int) l0.getZ();

			int[] min = new int[3];
			int[] max = new int[3];
			min[0] = x;
			min[1] = y;
			min[2] = z;
			max[0] = x+x_size;
			max[1] = y+y_size;
			max[2] = z+z_size;
			
			/* DEBUG LOGGING */
			if (Config.debugMode == true) {
				SendConsole.info("Visualization data: RegionID=" + id);
				SendConsole.info("Visualization data: MaxPoint=" + l1);
				SendConsole.info("Visualization data: MinPoint=" + l0);
			}

			if (tn.equalsIgnoreCase("cuboid")) {
				// Generate the 3D cuboid
				// Save data
				SaveData data = new SaveData(plugin);
				data.save3DCuboid(player, allowMove, viewName, viewType, min, max, null);
				
			} else {
				// Not supported
				SendGame.sendMessage(Messages.error_wg_notsupported, player);
			}
		} else {
			// Send message
			SendGame.sendMessage(
					Messages.error_nolocation.replace("{VIEW}", "region"),
					player);
		}
	}
}
