/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 30/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package vdw.maxim.bordervisualizer.visualizer;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import net.jzx7.regios.RegiosPlugin;
import net.jzx7.regiosapi.RegiosAPI;
import net.jzx7.regiosapi.regions.Region;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import vdw.maxim.bordervisualizer.BorderVisualizer;
import vdw.maxim.bordervisualizer.configuration.Config;
import vdw.maxim.bordervisualizer.locale.Messages;
import vdw.maxim.bordervisualizer.userinterface.SendConsole;
import vdw.maxim.bordervisualizer.userinterface.SendGame;
import vdw.maxim.bordervisualizer.utils.PermissionUtils;

import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class Visualize_Available {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Visualize_Available(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	public void visualize(Player player, Boolean allowMove, String viewName,
			int viewType, String displayName) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: AVAILABLE");
			SendConsole.info("Starting VisualizePlayer:" + player.getName());
		}

		// Save the player's location
		Location location = player.getLocation();

		// Get the world of the player
		World world = player.getWorld();

		// - Check the Towny plugin for available borders
		Boolean isTownyTown = false;
		if (BorderVisualizer.Towny != null) {
			// Get the town of the player location
			TownBlock current_TownBlock = TownyUniverse.getTownBlock(location);
			Town town = null;
			try {
				town = current_TownBlock.getTown();
				if (town != null) {
					isTownyTown = true;
				} else {
					isTownyTown = false;
				}
			} catch (Exception ex) {
			}
		}

		// - Check the Regios plugin for available borders
		Boolean isRegiosRegion = false;
		if (BorderVisualizer.Regios != null) {
			// Get the region
			RegiosPlugin regp = BorderVisualizer.Regios;
			RegiosAPI regapi = regp; // Init regios api
			Region region = null;
			// Get from position
			region = regapi.getRegion(location);
			
			if (region != null) {
				isRegiosRegion = true;
			} else {
				isRegiosRegion = false;
			}
		}

		// - Check the WorldGuard plugin for available borders
		Boolean isWorldGuardRegion = false;
		if (BorderVisualizer.WorldGuard != null) {
			// Get the region
			WorldGuardPlugin wg = BorderVisualizer.WorldGuard;
			RegionManager rm = wg.getRegionManager(world);
			// Get the region
			ProtectedRegion region = null;

			// Get from position
			ApplicableRegionSet rs = rm.getApplicableRegions(location);
			if (rs.iterator().hasNext()) {
				region = rs.iterator().next();
			}

			if (region != null) {
				isWorldGuardRegion = true;
			} else {
				isWorldGuardRegion = false;
			}
		}

		// - Check the GriefPrevention plugin for available borders
		Boolean isGriefPreventionClaim = false;
		if (BorderVisualizer.GriefPrevention != null) {
			// Get the claim at the players location
			GriefPrevention gp = BorderVisualizer.GriefPrevention;
			DataStore ds = gp.dataStore;
			Claim cl = ds.getClaimAt(location, false, null);
			// Check if townBlock exist
			if (cl != null) {
				isGriefPreventionClaim = true;
			} else {
				isGriefPreventionClaim = false;
			}
		}

		// Check if clain exists
		if (isGriefPreventionClaim == true
				&& (PermissionUtils.hasPermission("bordervisualizer.griefprevention", player))) {
			// USE GRIEFPREVENTION (0)
			Visualize_GriefPrevention_Claim visualizer = new Visualize_GriefPrevention_Claim(
					plugin);
			visualizer.visualize(player, allowMove, "GriefPrevention Claim",
					viewType, displayName);
			Visualize.createVisualize(plugin, player, "GriefPrevention Claim",
					viewType, displayName);
		} else {
			// Check if regios exists
			if (isRegiosRegion == true
					&& (PermissionUtils.hasPermission("bordervisualizer.regios.region",
							player))) {
				// USE REGION
				Visualize_Regios_Region visualizer = new Visualize_Regios_Region(
						plugin);
				visualizer.visualize(player, allowMove, "Regios Region",
						viewType, displayName);
				Visualize.createVisualize(plugin, player,
						"Regios Region", viewType, displayName);
			}else{
				// Check if region exists
				if (isWorldGuardRegion == true
						&& (PermissionUtils.hasPermission("bordervisualizer.worldguard.region",
								player))) {
					// USE REGION (1)
					Visualize_WorldGuard_Region visualizer = new Visualize_WorldGuard_Region(
							plugin);
					visualizer.visualize(player, allowMove, "WorldGuard Region",
							viewType, displayName);
					Visualize.createVisualize(plugin, player, "WorldGuard Region",
							viewType, displayName);
				} else {
					// Check if town exist
					if (isTownyTown == true
							&& (PermissionUtils.hasPermission("bordervisualizer.towny.town", player))) {
						// USE TOWN (2)
						Visualize_Towny_Town visualizer = new Visualize_Towny_Town(
								plugin);
						visualizer.visualize(player, allowMove, "Town", viewType,
								displayName);
						Visualize.createVisualize(plugin, player, "Town", viewType,
								displayName);
					} else {
						// USE CHUNK (3)
						if (PermissionUtils.hasPermission("bordervisualizer.chunk", player)) {
							Visualize_Chunk visualizer = new Visualize_Chunk(plugin);
							visualizer.visualize(player, allowMove, "Chunk",
									viewType, displayName);
							Visualize.createVisualize(plugin, player, "Chunk",
									viewType, displayName);
						} else {
							// If the player has no permission, show message
							SendGame.sendMessage(Messages.error_nopermission,
									player);
						}
					}
				}	
			}
		}
	}
}
