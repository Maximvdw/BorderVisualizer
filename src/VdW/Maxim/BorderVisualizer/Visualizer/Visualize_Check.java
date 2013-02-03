/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 30/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer.Visualizer;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
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
import VdW.Maxim.BorderVisualizer.GenerateView.Generate_2D_Square;
import VdW.Maxim.BorderVisualizer.Locale.Messages;
import VdW.Maxim.BorderVisualizer.UserInterface.SendConsole;
import VdW.Maxim.BorderVisualizer.UserInterface.SendGame;

public class Visualize_Check {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Visualize_Check(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	public void visualize_player(Player player, int blockid) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: CHECK Visualize");
			SendConsole.info("Searching VisualizePlayer:" + player.getName()
					+ ";" + blockid);
		}
		// Save the player's location
		Location location = player.getLocation();
		// Get World
		World world = location.getWorld();

		// Check if the player is in any border
		if (BorderVisualizer.Towny != null) {
			// First check if the player is inside a town
			// Get the town of the player location
			TownBlock current_TownBlock = TownyUniverse.getTownBlock(location);
			Town town = null;
			try {
				town = current_TownBlock.getTown();
			} catch (Exception ex) {
			}
			if (town != null) {
				// Display the town
				// Load visualizer
				Visualize_Towny_Town visualize = new Visualize_Towny_Town(
						plugin);
				if (!BorderVisualizer.bv_players_towny_town.contains(player)) {
					// Show blocks
					visualize.visualize_player(player, 1);
				} else {
					// Remove blocks
					visualize.remove_player(player, null, 1);
				}
				return;
			}
		}
		if (BorderVisualizer.WorldGuard != null) {
			// Then check if the player is inside a region
			// Get the region
			WorldGuardPlugin wg = BorderVisualizer.WorldGuard;
			RegionManager rm = wg.getRegionManager(world);
			// Get the region
			ProtectedRegion region = null;
			// Get from position
			ApplicableRegionSet rs = rm.getApplicableRegions(location);
			if (rs.iterator().hasNext()) {
				region = rs.iterator().next();
				// Display the region
				// Load visualizer
				Visualize_WorldGuard_Region visualize = new Visualize_WorldGuard_Region(
						plugin);
				if (!BorderVisualizer.bv_players_worldguard_region
						.contains(player)) {
					// Show blocks
					visualize.visualize_player(player, 1, null);
				} else {
					// Remove blocks
					visualize.remove_player(player, null, 1);
				}
				return;
			}
		}
		// Last Option is to display the chunk the player is in
		// Load visualizer
		Visualize_Chunk visualize = new Visualize_Chunk(plugin);
		if (!BorderVisualizer.bv_players_chunk.contains(player)) {
			// Show blocks
			visualize.visualize_player(player, 1);
		} else {
			// Remove blocks
			visualize.remove_player(player);
		}
	}
}
