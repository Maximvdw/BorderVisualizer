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
import VdW.Maxim.BorderVisualizer.GenerateView.ViewTypes;
import VdW.Maxim.BorderVisualizer.Locale.Messages;
import VdW.Maxim.BorderVisualizer.UserInterface.SendConsole;
import VdW.Maxim.BorderVisualizer.UserInterface.SendGame;
import VdW.Maxim.BorderVisualizer.Visualizer.Visualize;
import VdW.Maxim.BorderVisualizer.Visualizer.Visualize_Available;
import VdW.Maxim.BorderVisualizer.Visualizer.Visualize_Chunk;
import VdW.Maxim.BorderVisualizer.Visualizer.Visualize_Towny_Town;
import VdW.Maxim.BorderVisualizer.Visualizer.Visualize_Towny_TownBlock;
import VdW.Maxim.BorderVisualizer.Visualizer.Visualize_WorldGuard_Region;

public class CommandListener implements CommandExecutor {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public CommandListener(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	@Override
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
			// Check arguments
			if (arguments.length == 0) {
				// Visualize the border the player is in
				// Load visualizer

				// Get additional arguments
				int viewType = ViewTypes.VIEW_GLASS_WALL;
				String displayName = null;

				Visualize_Available visualizer = new Visualize_Available(plugin);
				visualizer.visualize(player, "Available", viewType, displayName);	
			} else {
				// Check argument
				argument = arguments[0];

				// Check for BorderVisualizer commands

				// Get additional arguments
				int viewType = ViewTypes.VIEW_GLASS_WALL;
				String displayName = null;
				for (int i = 1; i <= 3; i++) {
					try {
						/* DEBUG LOGGING */
						if (Config.debugMode == true) {
							SendConsole.info("Checking arguments...");
							SendConsole.info("arg: " + i + ";" + arguments[i]);
						}

						// Check the arguments
						if (arguments[i].equalsIgnoreCase("wall")) {
							// Set the viewType to wall
							viewType = ViewTypes.VIEW_GLASS_WALL;
						} else if (arguments[i].equalsIgnoreCase("frame")) {
							// Set the viewType to frame
							viewType = ViewTypes.VIEW_GLASS_FRAME;
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
				if (argument.equalsIgnoreCase("town"))
				{
					if (hasPermission("bordervisualizer.view.town", player))
					{
						Visualize_Towny_Town visualizer = new Visualize_Towny_Town(plugin);
						visualizer.visualize(player, "Town", viewType, displayName);
						Visualize.createVisualize(plugin,player, "Town", viewType, displayName);	
					}
				}else if (argument.equalsIgnoreCase("townblock"))
				{
					if (hasPermission("bordervisualizer.view.townblock", player))
					{
						Visualize_Towny_TownBlock visualizer = new Visualize_Towny_TownBlock(plugin);
						visualizer.visualize(player, "Town Block", viewType, displayName);
						Visualize.createVisualize(plugin,player, "Town Block", viewType, displayName);	
					}
				}else if (argument.equalsIgnoreCase("chunk"))
				{
					if (hasPermission("bordervisualizer.view.chunk", player))
					{
						Visualize_Chunk visualizer = new Visualize_Chunk(plugin);
						visualizer.visualize(player, "Chunk", viewType, displayName);
						Visualize.createVisualize(plugin,player, "Chunk", viewType, displayName);	
					}
				}else if (argument.equalsIgnoreCase("region"))
				{
					if (hasPermission("bordervisualizer.view.region", player))
					{
						Visualize_WorldGuard_Region visualizer = new Visualize_WorldGuard_Region(plugin);
						visualizer.visualize(player, "WorldGuard Region", viewType, displayName);
						Visualize.createVisualize(plugin,player, "WorldGuard Region", viewType, displayName);	
					}
				}
			}
		} else {
			// Unknow command (not registered)
			return false;
		}
		return true;
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
		// If the player has no permission, show message
		SendGame.sendMessage(Messages.error_nopermission, player);
		// Then return false
		return false;
	}
}