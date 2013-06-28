/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 31/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package vdw.maxim.bordervisualizer.playerlistener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import vdw.maxim.bordervisualizer.BorderVisualizer;
import vdw.maxim.bordervisualizer.configuration.Config;
import vdw.maxim.bordervisualizer.datastore.LoadData;
import vdw.maxim.bordervisualizer.datastore.dataLocation;
import vdw.maxim.bordervisualizer.datastore.dataPlayers;
import vdw.maxim.bordervisualizer.locale.Messages;
import vdw.maxim.bordervisualizer.userinterface.SendConsole;
import vdw.maxim.bordervisualizer.userinterface.SendGame;
import vdw.maxim.bordervisualizer.visualizer.Visualize;


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
		if (dataPlayers.contains(player)) {
			// Get the index of the player
			int index = dataPlayers.getIndex(player);
			LoadData data = new LoadData(plugin);
			if (!data.getAllowMove(player)){
				Location location_prev = (Location) dataLocation.getData(index);
				if (checkLocationsEqual(location,location_prev) == false) {
					// Show message
					SendGame.sendMessage(
							Messages.warning_movement.replace("{VIEW}", data.getViewName(player)),
							player);
					Visualize.deleteVisualize(plugin, player);
				}	
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
