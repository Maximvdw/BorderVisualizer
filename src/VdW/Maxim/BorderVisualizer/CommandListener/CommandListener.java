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
import VdW.Maxim.BorderVisualizer.Events.PlayerViewEvent;
import VdW.Maxim.BorderVisualizer.Locale.Messages;
import VdW.Maxim.BorderVisualizer.UserInterface.SendConsole;
import VdW.Maxim.BorderVisualizer.UserInterface.SendGame;
import VdW.Maxim.BorderVisualizer.Visualizer.Visualize_Check;
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
				Visualize_Check visualize = new Visualize_Check(
						plugin);
				visualize.visualize_player(player, 1);
			} else {
				// Check argument
				argument = arguments[0];
				
				// Check for BorderVisualizer commands
				
				// Check if the argument equals a loaded view
				PlayerViewEvent event = new PlayerViewEvent(plugin,argument,player,arguments[1]);
			    
				if (BorderVisualizer.bv_views.contains(argument))
				{
					// Raise PlayerViewEvent
					
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