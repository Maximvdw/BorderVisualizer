/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 31/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer.PlayerListener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import VdW.Maxim.BorderVisualizer.BorderVisualizer;
import VdW.Maxim.BorderVisualizer.Configuration.Config;
import VdW.Maxim.BorderVisualizer.Locale.Messages;
import VdW.Maxim.BorderVisualizer.UserInterface.SendConsole;
import VdW.Maxim.BorderVisualizer.UserInterface.SendGame;
import VdW.Maxim.BorderVisualizer.Visualizer.Visualize_Chunk;
import VdW.Maxim.BorderVisualizer.Visualizer.Visualize_Towny_Town;
import VdW.Maxim.BorderVisualizer.Visualizer.Visualize_Towny_TownBlock;
import VdW.Maxim.BorderVisualizer.Visualizer.Visualize_WorldGuard_Region;

public class PlayerListener_Movement implements Listener {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public PlayerListener_Movement(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	/* Check if the player moves so you can hide the blocks */
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerMove(PlayerMoveEvent e) {
		// Get Player name
		Player player = e.getPlayer();
		// Get Location
		Location location = player.getLocation();

		// Check if the player is in any list
		if (BorderVisualizer.bv_players_chunk.contains(player)) {
			// Get the index of the player
			int index = BorderVisualizer.bv_players_chunk.indexOf(player);
			Location location_prev = (Location) BorderVisualizer.bv_locations_chunk
					.get(index);
			if (checkLocationsEqual(location,location_prev) == false) {
				// Show message
				SendGame.sendMessage(
						Messages.warning_movement.replace("{VIEW}", "Chunk"),
						player);
				// Delete view
				Visualize_Chunk visualize = new Visualize_Chunk(plugin);
				visualize.remove_player(player);
			}
		}

		if (BorderVisualizer.bv_players_towny_townblock.contains(player)) {
			// Get the index of the player
			int index = BorderVisualizer.bv_players_towny_townblock
					.indexOf(player);
			Location location_prev = (Location) BorderVisualizer.bv_locations_towny_townblock
					.get(index);
			if (checkLocationsEqual(location,location_prev) == false) {
				// Show message
				SendGame.sendMessage(Messages.warning_movement.replace(
						"{VIEW}", "Town Block"), player);
				// Delete view
				Visualize_Towny_TownBlock visualize = new Visualize_Towny_TownBlock(
						plugin);
				visualize.remove_player(player);
			}
		}

		if (BorderVisualizer.bv_players_towny_town.contains(player)) {
			// Get the index of the player
			int index = BorderVisualizer.bv_players_towny_town.indexOf(player);
			Location location_prev = (Location) BorderVisualizer.bv_locations_towny_town
					.get(index);
			if (checkLocationsEqual(location,location_prev) == false) {
				// Show message
				SendGame.sendMessage(
						Messages.warning_movement.replace("{VIEW}", "Town"),
						player);
				// Delete view
				Visualize_Towny_Town visualize = new Visualize_Towny_Town(
						plugin);
				visualize.remove_player(player,null,1);
			}
		}

		if (BorderVisualizer.bv_players_worldguard_region.contains(player)) {
			// Get the index of the player
			int index = BorderVisualizer.bv_players_worldguard_region
					.indexOf(player);
			Location location_prev = (Location) BorderVisualizer.bv_locations_worldguard_region
					.get(index);
			if (checkLocationsEqual(location,location_prev) == false) {
				// Show message
				SendGame.sendMessage(Messages.warning_movement.replace(
						"{VIEW}", "WorldGuard Region"), player);
				// Delete view
				Visualize_WorldGuard_Region visualize = new Visualize_WorldGuard_Region(
						plugin);
				visualize.remove_player(player, null, 0);
			}
		}
	}

	/* Check if two locations are equal to eachother */
	public static boolean checkLocationsEqual(Location loc1, Location loc2) {
		// Get X,Y,Z from location 1
		String loc1_str = Math.round(loc1.getX()) + Math.round(loc1.getY()) + Math.round(loc1.getZ()) + "";
		// Get X,Y,Z from location 2
		String loc2_str = Math.round(loc2.getX()) + Math.round(loc2.getY()) + Math.round(loc2.getZ()) + "";
		
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("Checking if locations are Equal:");
			SendConsole.info("Location1_str:" + loc1_str);
			SendConsole.info("Location2_str:" + loc2_str);
		}
		
		// Check if they are equal
		if (loc1_str.equalsIgnoreCase(loc2_str)) {
			/* DEBUG LOGGING */
			if (Config.debugMode == true) {
				SendConsole.info("Returning TRUE for checkLocationsEqual");
			}
			
			return true;
		} else {
			/* DEBUG LOGGING */
			if (Config.debugMode == true) {
				SendConsole.info("Returning FALSE for checkLocationsEqual");
			}
			
			return false;
		}
	}
}
