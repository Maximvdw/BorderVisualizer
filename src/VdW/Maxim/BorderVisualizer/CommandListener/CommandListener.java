/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 30/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer.CommandListener;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import VdW.Maxim.BorderVisualizer.BorderVisualizer;
import VdW.Maxim.BorderVisualizer.Configuration.Config;
import VdW.Maxim.BorderVisualizer.GenerateView.ViewTypes;
import VdW.Maxim.BorderVisualizer.Locale.Messages;
import VdW.Maxim.BorderVisualizer.UserInterface.SendConsole;
import VdW.Maxim.BorderVisualizer.UserInterface.SendGame;
import VdW.Maxim.BorderVisualizer.Visualizer.Visualize;
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
		if (cmd.equalsIgnoreCase("bv") || cmd.equalsIgnoreCase("bordervisualizer")) {
			// Check arguments
			if (arguments.length == 0) {
				// Visualize the border the player is in
				// Load visualizer
				
			} else {
				// Check argument
				argument = arguments[0];
				
				// Check for BorderVisualizer commands
				
				// Check if the argument equals a loaded view
				if (BorderVisualizer.bv_view_command.contains(argument))
				{
					// Get additional arguments
					int viewType = ViewTypes.VIEW_GLASS_WALL;
					String displayName = null;
					for (int i=1;i<=3;i++)
					{	
						try{
							/* DEBUG LOGGING */
							if (Config.debugMode == true) {
								SendConsole.info("Checking arguments...");
								SendConsole.info("arg: " + i + ";" + arguments[i]);
							}
							
							// Check the arguments
							if (arguments[i].equalsIgnoreCase("wall"))
							{
								// Set the viewType to wall
								viewType = ViewTypes.VIEW_GLASS_WALL;
							}else if(arguments[i].equalsIgnoreCase("frame")){
								// Set the viewType to frame
								viewType = ViewTypes.VIEW_GLASS_FRAME;
							}else{
								// Display argument
								/* DEBUG LOGGING */
								if (Config.debugMode == true) {
									SendConsole.info("Set displayName: '" + arguments[i] + "'");
								}
								displayName = arguments[i];
							}	
						}catch (Exception ex)
						{
							
						}
					}
					
					// Raise the visualizer
					int index = BorderVisualizer.bv_view_command.indexOf(argument);
					String viewName = BorderVisualizer.bv_view_name.get(index);
					String pluginName = BorderVisualizer.bv_view_plugin.get(index);
					Visualize.createVisualize(plugin, player, viewName, viewType, displayName, pluginName);
				}
			}
		}else{
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