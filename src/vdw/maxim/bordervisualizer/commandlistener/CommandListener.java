/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 30/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package vdw.maxim.bordervisualizer.commandlistener;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import vdw.maxim.bordervisualizer.BorderVisualizer;
import vdw.maxim.bordervisualizer.configuration.Config;
import vdw.maxim.bordervisualizer.datastore.LoadData;
import vdw.maxim.bordervisualizer.datastore.ResetData;
import vdw.maxim.bordervisualizer.datastore.dataPlayers;
import vdw.maxim.bordervisualizer.generateview.ViewTypes;
import vdw.maxim.bordervisualizer.locale.Messages;
import vdw.maxim.bordervisualizer.userinterface.SendConsole;
import vdw.maxim.bordervisualizer.userinterface.SendGame;
import vdw.maxim.bordervisualizer.userinterface.SendHelp;
import vdw.maxim.bordervisualizer.userinterface.SendUnknown;
import vdw.maxim.bordervisualizer.utils.PermissionUtils;
import vdw.maxim.bordervisualizer.visualizer.Visualize;
import vdw.maxim.bordervisualizer.visualizer.Visualize_Available;
import vdw.maxim.bordervisualizer.visualizer.Visualize_Chunk;
import vdw.maxim.bordervisualizer.visualizer.Visualize_Factions_Faction;
import vdw.maxim.bordervisualizer.visualizer.Visualize_Factions_FactionBlock;
import vdw.maxim.bordervisualizer.visualizer.Visualize_GriefPrevention_Claim;
import vdw.maxim.bordervisualizer.visualizer.Visualize_Regios_Region;
import vdw.maxim.bordervisualizer.visualizer.Visualize_Towny_Town;
import vdw.maxim.bordervisualizer.visualizer.Visualize_Towny_TownBlock;
import vdw.maxim.bordervisualizer.visualizer.Visualize_WorldGuard_Region;

public class CommandListener implements CommandExecutor {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	/**
	 * Initialize the command listener
	 * 
	 * @param plugin
	 *            - BorderVisualizer class
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
			if (player != null) {
				if (dataPlayers.contains(player)) {
					// Delete the view
					SendGame.sendMessage(Messages.confirm_removed, player); // Show
																			// message
																			// to
																			// confirm
					Visualize.deleteVisualize(plugin, player);
					return true;
				}
			}

			// Check arguments
			if (arguments.length == 0) {
				if (player != null) {
					// Visualize the border the player is in
					// Load visualizer

					// Get additional arguments
					int viewType = ViewTypes.VIEW_GLASS_WALL;
					String displayName = null;
					// Check if wand
					ItemStack is = player.getItemInHand();
					if (is.getType() == Material.WOOD_HOE){
						viewType = ViewTypes.VIEW_GLASS_WAND;
						// Send Message
						SendGame.sendMessage(
								Messages.confirm_wand,
								player);
					}
					Visualize_Available visualizer = new Visualize_Available(
							plugin);
					visualizer.visualize(player, false, "Available", viewType,
							displayName);
					return true; // Exit routine
				} else {
					// Show the plugin help
					SendHelp.help_general(player);
					return true; // Exit routine
				}
			} else {
				// Check argument
				argument = arguments[0];
				// Check for build in arguments
				if (argument.equalsIgnoreCase("help")) {
					// Show the plugin help
					SendHelp.help_general(player);
					return true; // Exit routine
				} else if (argument.equalsIgnoreCase("about")) {
					// Show info about the plugin

					return true; // Exit routine
				} else if (argument.equalsIgnoreCase("admin")) {
					// Show admin help
					SendHelp.help_admin(player);
					return true; // Exit routine
				} else if (argument.equalsIgnoreCase("reload")) {
					// Reload the plugin config
					BorderVisualizer.BVconfig.loadYamls();
					Config.initConfig();
					// Show message
					SendConsole.info("Checking for open border views...");
					// Delete all open views
					Player[] players = plugin.getServer().getOnlinePlayers();
					// Now check every player for open view
					for (Player pl : players) {
						// Check if on list
						if (dataPlayers.contains(pl)) {
							// Delete view
							Visualize.deleteVisualize(plugin, pl);
							SendGame.sendMessage(Messages.warning_forcereload,
									pl);
						}
					}
					SendUnknown.sendMessage(Messages.confirm_reload, player);
					return true;
				}

				// Check for BorderVisualizer commands

				// Get additional arguments
				int viewType = ViewTypes.VIEW_GLASS_WALL;
				boolean defaultViewType = true;
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
							defaultViewType = false;
						} else if (arguments[i].equalsIgnoreCase("-f")) {
							// Set the viewType to frame
							viewType = ViewTypes.VIEW_GLASS_FRAME;
							defaultViewType = false;
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
				// Check if wand
				ItemStack is = player.getItemInHand();
				if (is.getType() == Material.WOOD_HOE){
					viewType = ViewTypes.VIEW_GLASS_WAND;
					defaultViewType = false;
					// Send Message
					SendGame.sendMessage(
							Messages.confirm_wand,
							player);
				}
				
				// Check for the view
				if (argument.equalsIgnoreCase("town")) {
					if (BorderVisualizer.Towny != null) {
						if (PermissionUtils.hasPermission(
								"bordervisualizer.town.town", player)) {
							if (defaultViewType == true) {
								viewType = Config.squareView;
							} // Change viewtype
							Visualize_Towny_Town visualizer = new Visualize_Towny_Town(
									plugin);
							visualizer.visualize(player, allowMove, "Town",
									viewType, displayName);
							Visualize.createVisualize(plugin, player, "Town",
									viewType, displayName);
						}
					} else {
						// Plugin not found
						SendGame.sendMessage(Messages.error_plugin_notfound,
								player);
					}
				} else if (argument.equalsIgnoreCase("townblock")) {
					if (BorderVisualizer.Towny != null) {
						if (PermissionUtils.hasPermission(
								"bordervisualizer.towny.townblock", player)) {
							if (defaultViewType == true) {
								viewType = Config.squareView;
							} // Change viewtype
							Visualize_Towny_TownBlock visualizer = new Visualize_Towny_TownBlock(
									plugin);
							visualizer.visualize(player, allowMove,
									"Town Block", viewType, displayName);
							Visualize.createVisualize(plugin, player,
									"Town Block", viewType, displayName);
						}
					} else {
						// Plugin not found
						SendGame.sendMessage(Messages.error_plugin_notfound,
								player);
					}
				} else if (argument.equalsIgnoreCase("chunk")) {
					if (PermissionUtils.hasPermission("bordervisualizer.chunk",
							player)) {
						if (defaultViewType == true) {
							viewType = Config.squareView;
						} // Change viewtype
						Visualize_Chunk visualizer = new Visualize_Chunk(plugin);
						visualizer.visualize(player, allowMove, "Chunk",
								viewType, displayName);
						Visualize.createVisualize(plugin, player, "Chunk",
								viewType, displayName);
					}
				} else if (argument.equalsIgnoreCase("factionblock")) {
					if (BorderVisualizer.Factions != null) {
						if (PermissionUtils.hasPermission(
								"bordervisualizer.factions.factionblock",
								player)) {
							if (defaultViewType == true) {
								viewType = Config.squareView;
							} // Change viewtype
							Visualize_Factions_FactionBlock visualizer = new Visualize_Factions_FactionBlock(
									plugin);
							visualizer.visualize(player, allowMove,
									"Faction Block", viewType, displayName);
							Visualize.createVisualize(plugin, player,
									"Faction Block", viewType, displayName);
						}
					} else {
						// Plugin not found
						SendGame.sendMessage(Messages.error_plugin_notfound,
								player);
					}
				} else if (argument.equalsIgnoreCase("faction")) {
					if (BorderVisualizer.Factions != null) {
						if (PermissionUtils.hasPermission(
								"bordervisualizer.factions.faction", player)) {
							if (defaultViewType == true) {
								viewType = Config.squareView;
							} // Change viewtype
							Visualize_Factions_Faction visualizer = new Visualize_Factions_Faction(
									plugin);
							visualizer.visualize(player, allowMove, "Faction",
									viewType, displayName);
							Visualize.createVisualize(plugin, player,
									"Faction", viewType, displayName);
						}
					} else {
						// Plugin not found
						SendGame.sendMessage(Messages.error_plugin_notfound,
								player);
					}
				} else if (argument.equalsIgnoreCase("region")) {
					// Check if Regios or Worldguard
					if (BorderVisualizer.WorldGuard != null) {
						if (PermissionUtils.hasPermission(
								"bordervisualizer.worldguard.region", player)) {
							if (defaultViewType == true) {
								viewType = Config.cuboidView;
							} // Change viewtype
							Visualize_WorldGuard_Region visualizer = new Visualize_WorldGuard_Region(
									plugin);
							visualizer.visualize(player, allowMove,
									"WorldGuard Region", viewType, displayName);
							Visualize.createVisualize(plugin, player,
									"WorldGuard Region", viewType, displayName);
						}
					} else if (BorderVisualizer.Regios != null) {
						if (PermissionUtils.hasPermission(
								"bordervisualizer.regios.region", player)) {
							if (defaultViewType == true) {
								viewType = Config.cuboidView;
							} // Change viewtype
							Visualize_Regios_Region visualizer = new Visualize_Regios_Region(
									plugin);
							visualizer.visualize(player, allowMove,
									"Regios Region", viewType, displayName);
							Visualize.createVisualize(plugin, player,
									"Regios Region", viewType, displayName);
						}
					} else {
						// Plugin not found
						SendGame.sendMessage(Messages.error_plugin_notfound,
								player);
					}
				} else if (argument.equalsIgnoreCase("regios")) {
					if (BorderVisualizer.Regios != null) {
						if (PermissionUtils.hasPermission(
								"bordervisualizer.regios.region", player)) {
							if (defaultViewType == true) {
								viewType = Config.cuboidView;
							} // Change viewtype
							Visualize_Regios_Region visualizer = new Visualize_Regios_Region(
									plugin);
							visualizer.visualize(player, allowMove,
									"Regios Region", viewType, displayName);
							Visualize.createVisualize(plugin, player,
									"Regios Region", viewType, displayName);
						}
					} else {
						// Plugin not found
						SendGame.sendMessage(Messages.error_plugin_notfound,
								player);
					}
				} else if (argument.equalsIgnoreCase("griefprevention")) {
					if (BorderVisualizer.GriefPrevention != null) {
						if (PermissionUtils.hasPermission(
								"bordervisualizer.griefprevention", player)) {
							if (defaultViewType == true) {
								viewType = Config.rectangleView;
							} // Change viewtype
							Visualize_GriefPrevention_Claim visualizer = new Visualize_GriefPrevention_Claim(
									plugin);
							visualizer.visualize(player, allowMove,
									"GriefPrevention Claim", viewType,
									displayName);
							Visualize.createVisualize(plugin, player,
									"GriefPrevention Claim", viewType,
									displayName);
						}
					} else {
						// Plugin not found
						SendGame.sendMessage(Messages.error_plugin_notfound,
								player);
					}
				}
			}
		} else {
			// Unknow command (not registered)
			return false;
		}
		return true;
	}
}