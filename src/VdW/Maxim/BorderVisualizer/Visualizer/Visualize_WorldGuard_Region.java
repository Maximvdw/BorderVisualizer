/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 30/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer.Visualizer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import VdW.Maxim.BorderVisualizer.BorderVisualizer;
import VdW.Maxim.BorderVisualizer.Configuration.Config;
import VdW.Maxim.BorderVisualizer.GenerateView.Generate_3D_Cuboid;
import VdW.Maxim.BorderVisualizer.GenerateView.Generate_3D_CuboidFrame;
import VdW.Maxim.BorderVisualizer.Locale.Messages;
import VdW.Maxim.BorderVisualizer.UserInterface.SendConsole;
import VdW.Maxim.BorderVisualizer.UserInterface.SendGame;

public class Visualize_WorldGuard_Region {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Visualize_WorldGuard_Region(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	public void visualize_player(final Player player, int blockid,
			final String regionName) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: WORLDGUARD_REGION");
			SendConsole.info("Starting VisualizePlayer:" + player.getName()
					+ ";" + blockid);
		}
		// Save the player's location
		Location location = player.getLocation();

		// Get World
		World world = location.getWorld();

		// Get the region
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

			/* DEBUG LOGGING */
			if (Config.debugMode == true) {
				SendConsole.info("Visualization data: RegionID=" + id);
				SendConsole.info("Visualization data: MaxPoint=" + l1);
				SendConsole.info("Visualization data: MinPoint=" + l0);
			}

			if (tn.equalsIgnoreCase("cuboid")) {
				// Generate the 3D cuboid
				
				// Block to replace the cuboid
				Material block = Material.GLASS;
				
				Generate_3D_CuboidFrame generator = new Generate_3D_CuboidFrame(plugin);
				generator.generate(player, x, y, z, x_size, y_size, z_size,
						block, null);
				
				// Save data
				BorderVisualizer.bv_players_worldguard_region.add(player);
				BorderVisualizer.bv_regionName_worldguard_region.add(regionName);
				BorderVisualizer.bv_locations_worldguard_region.add(location);

				// Send Message
				SendGame.sendMessage(
						Messages.config_visualized.replace("{VIEW}", "Region"),
						player);
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

	public void remove_player(final Player player, final String nextView,
			int blockid) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: WORLDGUARD_REGION");
			SendConsole.info("Starting RemovePlayer:" + player.getName());
		}

		// Get the players location before executing the command
		int index = BorderVisualizer.bv_players_worldguard_region
				.indexOf(player);
		String regionName = BorderVisualizer.bv_regionName_worldguard_region
				.get(index);
		Location location = BorderVisualizer.bv_locations_worldguard_region
				.get(index);

		// Check if the player asked for 'A new region
		// visualization'
		if ((nextView != null) && (regionName != nextView)) {
			SendGame.sendMessage(Messages.warning_nextview.replace("{VIEW}",
					"WorldGuard Region"), player);
		}

		// Remove the saved data
		BorderVisualizer.bv_players_worldguard_region.remove(player);
		BorderVisualizer.bv_regionName_worldguard_region.remove(index);
		BorderVisualizer.bv_locations_worldguard_region.remove(index);

		// Get World
		World world = location.getWorld();

		// Get the region
		WorldGuardPlugin wg = BorderVisualizer.WorldGuard;
		RegionManager rm = wg.getRegionManager(world);

		// Get the region
		ProtectedRegion region;

		if (regionName == null) {
			// Get from position
			ApplicableRegionSet rs = rm.getApplicableRegions(location);
			region = rs.iterator().next();
		} else {
			// Get from text
			region = rm.getRegion(regionName);
		}

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

		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("Visualization data: RegionID=" + id);
			SendConsole.info("Visualization data: MaxPoint=" + l1);
			SendConsole.info("Visualization data: MinPoint=" + l0);
		}

		if (tn.equalsIgnoreCase("cuboid")) {
			// Generate the 3D cuboid
			Material block = Material.AIR; // Block to replace
											// it with
			Generate_3D_Cuboid generator = new Generate_3D_Cuboid(plugin);
			generator.generate(player, x, y, z, x_size, y_size, z_size, block,
					null);
		} else {
			// Not supported
			SendGame.sendMessage(Messages.error_wg_notsupported, player);
		}
	}
}
