/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 30/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer.CommandListener;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import VdW.Maxim.BorderVisualizer.BorderVisualizer;
import VdW.Maxim.BorderVisualizer.Configuration.Config;
import VdW.Maxim.BorderVisualizer.Locale.Messages;
import VdW.Maxim.BorderVisualizer.UserInterface.SendConsole;
import VdW.Maxim.BorderVisualizer.UserInterface.SendGame;
import VdW.Maxim.BorderVisualizer.Visualizer.Visualize_Chunk;
import VdW.Maxim.BorderVisualizer.Visualizer.Visualize_Towny_Town;
import VdW.Maxim.BorderVisualizer.Visualizer.Visualize_Towny_TownBlock;
import VdW.Maxim.BorderVisualizer.Visualizer.Visualize_WorldGuard_Region;

public class CommandListener_General implements CommandExecutor {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public CommandListener_General(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String cmd,
			String[] arguments) {
		Player player = null;
		String argument = null;
		Boolean isConsole = false;

		// Check if it is a real player
		if (sender instanceof Player) {
			// Add playerID to player
			player = (Player) sender;
		}
		// Check if it is the console
		if (player == null) {
			// Set console to true
			isConsole = true;
		}

		// Check if the player entered a valid command
		if (cmd.equalsIgnoreCase("bv")) {
			// Check arguments
			if (arguments.length == 0) {
				// Show help
			} else {
				// Check argument
				argument = arguments[0];
				// --------------
				if (argument.equalsIgnoreCase("townblock")) {
					if (hasPermission("bordervisualizer.view.townblock", player) == true) {
						// Load visualizer
						Visualize_Towny_TownBlock visualize = new Visualize_Towny_TownBlock(
								plugin);
						if (!BorderVisualizer.bv_players_towny_townblock
								.contains(player)) {
							// Show blocks
							visualize.visualize_player(player, 1);
						} else {
							// Remove blocks
							visualize.remove_player(player);
						}
					}
				} else if (argument.equalsIgnoreCase("town")) {
					if (hasPermission("bordervisualizer.view.town", player) == true) {
						// Load visualizer
						Visualize_Towny_Town visualize = new Visualize_Towny_Town(
								plugin);
						if (!BorderVisualizer.bv_players_towny_town
								.contains(player)) {
							// Show blocks
							visualize.visualize_player(player, 1);
						} else {
							// Remove blocks
							visualize.remove_player(player,null,1);
						}
					}
				} else if (argument.equalsIgnoreCase("chunk")) {
					if (hasPermission("bordervisualizer.view.chunk", player) == true) {
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
				} else if (argument.equalsIgnoreCase("region")) {
					if (hasPermission("bordervisualizer.view.region", player) == true) {
						// Load visualizer
						Visualize_WorldGuard_Region visualize = new Visualize_WorldGuard_Region(
								plugin);
						if (!BorderVisualizer.bv_players_worldguard_region
								.contains(player)) {
							if (arguments.length == 1) {
								// Show blocks from position
								visualize.visualize_player(player, 1, null);
							} else {
								// Show blocks from name
								argument = arguments[1];
								visualize.visualize_player(player, 1, argument);
							}
						} else {
							if (arguments.length == 1) {
								// Remove blocks
								visualize.remove_player(player, null, 0);
							} else {
								// Show blocks
								argument = arguments[1];
								visualize.remove_player(player, argument, 0);
							}
						}
					}
				}
			}
		}
		return false;
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
					|| (player.isOp() && Config.allowOpPermission)) {
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
