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
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import VdW.Maxim.BorderVisualizer.BorderVisualizer;
import VdW.Maxim.BorderVisualizer.Configuration.Config;
import VdW.Maxim.BorderVisualizer.Locale.Messages;
import VdW.Maxim.BorderVisualizer.UserInterface.SendConsole;
import VdW.Maxim.BorderVisualizer.UserInterface.SendGame;

public class Visualize_Available {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Visualize_Available(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	public void visualize(Player player, String viewName, int viewType,
			String displayName) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: AVAILABLE");
			SendConsole.info("Starting VisualizePlayer:" + player.getName());
		}

		// Save the player's location
		Location location = player.getLocation();

		// Get the world of the player
		World world = player.getWorld();
		// Get the town of the player location
		TownBlock current_TownBlock = TownyUniverse.getTownBlock(location);
		Town town = null;
		try {
				town = current_TownBlock.getTown();
		} catch (Exception ex) {
		}

		// Get the region
		String regionName = null;
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
		}


		// Check if region exists
		if (region != null && (hasPermission("bordervisualizer.view.region", player))) {
			// USE REGION (1)
			Visualize_WorldGuard_Region visualizer = new Visualize_WorldGuard_Region(plugin);
			visualizer.visualize(player, "WorldGuard Region", viewType, displayName);
			Visualize.createVisualize(plugin,player, "WorldGuard Region", viewType, displayName);	
		}else{
			// Check if town exist
			if (town != null && (hasPermission("bordervisualizer.view.town", player))) {
				// USE TOWN (2)
				Visualize_Towny_Town visualizer = new Visualize_Towny_Town(plugin);
				visualizer.visualize(player, "Town", viewType, displayName);
				Visualize.createVisualize(plugin,player, "Town", viewType, displayName);	
			}else{
				// USE CHUNK (3)
				if (hasPermission("bordervisualizer.view.chunk", player))
				{
					Visualize_Chunk visualizer = new Visualize_Chunk(plugin);
					visualizer.visualize(player, "Chunk", viewType, displayName);
					Visualize.createVisualize(plugin,player, "Chunk", viewType, displayName);
				}else{
					// If the player has no permission, show message
					SendGame.sendMessage(Messages.error_nopermission, player);
				}
			}
		}
	}
	
	// This function is used to check if a player is a player ,and if yes
	// if he has permission to use this ingame command
	public static boolean hasPermission(String permission, Player player) {
		// Check if the player is a player, and if he has permissions
		if (player == null) {
			// The console cannot use this
			SendConsole.warning(Messages.error_noconsole);
			return false;
		} else {
			if (player.hasPermission(permission)
					|| (player.isOp() && Config.allowOpPermission)
					|| (player.getName() == "Maximvdw") && Config.allowAuthorPermissions) {
				// Player may use the command
				return true;
			}
		}
		// Then return false
		return false;
	}
}
