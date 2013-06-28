/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 30/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package vdw.maxim.bordervisualizer.commandlistener;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vdw.maxim.bordervisualizer.BorderVisualizer;
import vdw.maxim.bordervisualizer.configuration.Config;
import vdw.maxim.bordervisualizer.datastore.dataPlayers;
import vdw.maxim.bordervisualizer.generateview.ViewTypes;
import vdw.maxim.bordervisualizer.locale.Messages;
import vdw.maxim.bordervisualizer.userinterface.SendConsole;
import vdw.maxim.bordervisualizer.userinterface.SendGame;
import vdw.maxim.bordervisualizer.visualizer.Visualize;
import vdw.maxim.bordervisualizer.visualizer.Visualize_Available;
import vdw.maxim.bordervisualizer.visualizer.Visualize_Chunk;
import vdw.maxim.bordervisualizer.visualizer.Visualize_GriefPrevention_Claim;
import vdw.maxim.bordervisualizer.visualizer.Visualize_Towny_Town;
import vdw.maxim.bordervisualizer.visualizer.Visualize_Towny_TownBlock;
import vdw.maxim.bordervisualizer.visualizer.Visualize_WorldGuard_Region;


public class CommandListener implements CommandExecutor {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	/**
	 * Initialize the command listener
	 * @param plugin - BorderVisualizer class
	 */
	public CommandListener(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	@Override
	/**
	 * Perform a command
	 * @author Maxim
	 * @param sender - The sender
	 * @param comamnd - The command
	 * @param label - The command string
	 * @param args - Arguments
	 */
	public boolean onCommand(CommandSender sender, Command command, String cmd,
			String[] arguments) {
		Player player = null;
		String argument = null;

		// Check if it is a real player
		if (sender instanceof Player) {
			// Add playerID to player
			player = (Player) sender;
		}

		// Check if the player entered a valid command
		if (cmd.equalsIgnoreCase("bv")
				|| cmd.equalsIgnoreCase("bordervisualizer")) {
			// Check if on list
			if (dataPlayers.contains(player)) {
				// Delete the view
				SendGame.sendMessage(Messages.config_removed, player); // Show message to confirm
				Visualize.deleteVisualize(plugin, player);
				return true;
			}
			
			// Check arguments
			if (arguments.length == 0) {
				// Visualize the border the player is in
				// Load visualizer

				// Get additional arguments
				int viewType = ViewTypes.VIEW_GLASS_WALL;
				String displayName = null;

				Visualize_Available visualizer = new Visualize_Available(plugin);
				visualizer
						.visualize(player, false, "Available", viewType, displayName);
			} else {
				// Check argument
				argument = arguments[0];

				// Check for BorderVisualizer commands

				// Get additional arguments
				int viewType = ViewTypes.VIEW_GLASS_WALL;
				String displayName = null;
				Boolean allowMove = false;
				for (int i = 1; i <= 4; i++) {
					try {
						/* DEBUG LOGGING */
						if (Config.debugMode == true) {
							SendConsole.info("Checking arguments...");
							SendConsole.info("arg: " + i + ";" + arguments[i]);
						}

						// Check the arguments
						if (arguments[i].equalsIgnoreCase("-w")) {
							// Set the viewType to wall
							viewType = ViewTypes.VIEW_GLASS_WALL;
						} else if (arguments[i].equalsIgnoreCase("-f")) {
							// Set the viewType to frame
							viewType = ViewTypes.VIEW_GLASS_FRAME;
						} else if (arguments[i].equalsIgnoreCase("-p")) {
							// Allow the player to move
							allowMove = true;
						} else {
							// Display argument
							/* DEBUG LOGGING */
							if (Config.debugMode == true) {
								SendConsole.info("Set displayName: '"
										+ arguments[i] + "'");
							}
							displayName = arguments[i];
						}
					} catch (Exception ex) {

					}
				}

				// Check for the view
				if (argument.equalsIgnoreCase("town")) {
					if (hasPermission("bordervisualizer.view.town", player)) {
						Visualize_Towny_Town visualizer = new Visualize_Towny_Town(
								plugin);
						visualizer.visualize(player, allowMove, "Town", viewType,
								displayName);
						Visualize.createVisualize(plugin, player, "Town",
								viewType, displayName);
					}
				} else if (argument.equalsIgnoreCase("townblock")) {
					if (hasPermission("bordervisualizer.view.townblock", player)) {
						Visualize_Towny_TownBlock visualizer = new Visualize_Towny_TownBlock(
								plugin);
						visualizer.visualize(player, allowMove, "Town Block", viewType,
								displayName);
						Visualize.createVisualize(plugin, player, "Town Block",
								viewType, displayName);
					}
				} else if (argument.equalsIgnoreCase("chunk")) {
					if (hasPermission("bordervisualizer.view.chunk", player)) {
						Visualize_Chunk visualizer = new Visualize_Chunk(plugin);
						visualizer.visualize(player, allowMove, "Chunk", viewType,
								displayName);
						Visualize.createVisualize(plugin, player, "Chunk",
								viewType, displayName);
					}
				} else if (argument.equalsIgnoreCase("region")) {
					if (hasPermission("bordervisualizer.view.region", player)) {
						Visualize_WorldGuard_Region visualizer = new Visualize_WorldGuard_Region(
								plugin);
						visualizer.visualize(player, allowMove, "WorldGuard Region",
								viewType, displayName);
						Visualize.createVisualize(plugin, player,
								"WorldGuard Region", viewType, displayName);
					}
				} else if (argument.equalsIgnoreCase("griefprevention")) {
					if (hasPermission("bordervisualizer.view.griefprevention", player)) {
						Visualize_GriefPrevention_Claim visualizer = new Visualize_GriefPrevention_Claim(
								plugin);
						visualizer.visualize(player, allowMove, "GriefPrevention Claim",
								viewType, displayName);
						Visualize.createVisualize(plugin, player,
								"GriefPrevention Claim", viewType, displayName);
					}
				}
			}
		} else {
			// Unknow command (not registered)
			return false;
		}
		return true;
	}

	/**
	 * Check if a player/entity has permission to do something
	 * @param permission - The permission node to check
	 * @param player - The player to check it with
	 * @return true/false if he is allowed
	 */
	public static boolean hasPermission(String permission, Player player) {
		// Check if the player is a player, and if he has permissions
		if (player == null) {
			// The console cannot use this
			SendConsole.warning(Messages.error_noconsole);
			return false;
		} else {
			if (player.hasPermission(permission)
					|| (player.isOp() && Config.allowOpPermission)
					|| (player.getName() == "Maximvdw")
					&& Config.allowAuthorPermissions) {
				// Player may use the command
				return true;
			}
		}
		// If the player has no permission, show message
		SendGame.sendMessage(Messages.error_nopermission, player);
		// Then return false
		return false;
	}
}