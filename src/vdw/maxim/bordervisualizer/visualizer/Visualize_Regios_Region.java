/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 30/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package vdw.maxim.bordervisualizer.visualizer;

import java.awt.geom.Rectangle2D;

import net.jzx7.regios.RegiosPlugin;
import net.jzx7.regiosapi.RegiosAPI;
import net.jzx7.regiosapi.location.RegiosPoint;
import net.jzx7.regiosapi.regions.CuboidRegion;
import net.jzx7.regiosapi.regions.PolyRegion;
import net.jzx7.regiosapi.regions.Region;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import vdw.maxim.bordervisualizer.BorderVisualizer;
import vdw.maxim.bordervisualizer.configuration.Config;
import vdw.maxim.bordervisualizer.datastore.SaveData;
import vdw.maxim.bordervisualizer.locale.Messages;
import vdw.maxim.bordervisualizer.userinterface.SendConsole;
import vdw.maxim.bordervisualizer.userinterface.SendGame;

public class Visualize_Regios_Region {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Visualize_Regios_Region(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	public void visualize(Player player, Boolean allowMove, String viewName,
			int viewType, String displayName) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: REGIOS_REGION");
			SendConsole.info("Starting VisualizePlayer:" + player.getName());
		}
		// Save the player's location
		Location location = player.getLocation();

		// Get the region
		String regionName = displayName;
		RegiosPlugin regp = BorderVisualizer.Regios;
		RegiosAPI regapi = regp; // Init regios api
		Region region = null;
		if (regionName == null) {
			// Get from position
			region = regapi.getRegion(location);
		} else {
			// Get from text
			region = regapi.getRegion(regionName);
		}

		// Check if region exists
		if (region != null) {
			if (region instanceof CuboidRegion) {
				RegiosPoint l0 = ((CuboidRegion) (region)).getL1();
				RegiosPoint l1 = ((CuboidRegion) (region)).getL2();

				int x = (int) l1.getX();
				int y = (int) l0.getY();
				int z = (int) l0.getZ();
				int x_size = (int) (l1.getX()) - (int) l0.getX();
				int y_size = (int) (l1.getY()) - (int) l0.getY();
				int z_size = (int) (l1.getZ()) - (int) l0.getZ();

				int[] min = new int[3];
				int[] max = new int[3];
				min[0] = x;
				min[1] = y;
				min[2] = z;
				max[0] = x + x_size;
				max[1] = y + y_size;
				max[2] = z + z_size;

				/* DEBUG LOGGING */
				if (Config.debugMode == true) {
					SendConsole.info("Visualization data: MaxPoint="
							+ l1.getX() + ";" + l1.getY() + ";" + l1.getZ());
					SendConsole.info("Visualization data: MinPoint="
							+ l0.getX() + ";" + l0.getY() + ";" + l0.getZ());
				}

				// Generate the 3D cuboid
				// Save data
				SaveData data = new SaveData(plugin);
				data.save3DCuboid(player, allowMove, viewName, viewType, min,
						max, null);
			} else if (region instanceof PolyRegion){
				PolyRegion pr = (PolyRegion) region;
				Rectangle2D rect = pr.get2DPolygon().getBounds2D();
				RegiosPoint l0 = new RegiosPoint(region.getWorld(), rect.getMinX(), pr.getMinY(), rect.getMinY());
				RegiosPoint l1 = new RegiosPoint(region.getWorld(), rect.getMaxX(), pr.getMaxY(), rect.getMaxY());

				int x = (int) l1.getX();
				int y = (int) l0.getY();
				int z = (int) l0.getZ();
				int x_size = (int) (l1.getX()) - (int) l0.getX();
				int y_size = (int) (l1.getY()) - (int) l0.getY();
				int z_size = (int) (l1.getZ()) - (int) l0.getZ();

				int[] min = new int[3];
				int[] max = new int[3];
				min[0] = x;
				min[1] = y;
				min[2] = z;
				max[0] = x + x_size;
				max[1] = y + y_size;
				max[2] = z + z_size;

				/* DEBUG LOGGING */
				if (Config.debugMode == true) {
					SendConsole.info("Visualization data: MaxPoint="
							+ l1.getX() + ";" + l1.getY() + ";" + l1.getZ());
					SendConsole.info("Visualization data: MinPoint="
							+ l0.getX() + ";" + l0.getY() + ";" + l0.getZ());
				}

				// Generate the 3D cuboid
				// Save data
				SaveData data = new SaveData(plugin);
				data.save3DCuboid(player, allowMove, viewName, viewType, min,
						max, null);
				// Not supported
				SendGame.sendMessage(Messages.warning_notfullsupport, player);
			}else{
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
