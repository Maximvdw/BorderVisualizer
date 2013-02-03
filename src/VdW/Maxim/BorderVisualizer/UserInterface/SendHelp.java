/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 31/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer.UserInterface;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import VdW.Maxim.BorderVisualizer.Configuration.Config;
import VdW.Maxim.BorderVisualizer.Locale.Messages;

public class SendHelp {
	// Send general help
	public static void help_general(String message, Player player) {
		// Check if player has permission
		if (hasPermission("bordervisualizer.help", player)) {
			
		}
	}

	// This function is used to check if a player is a player ,and if yes
	// if he has permission to use this ingame command
	public static boolean hasPermission(String permission, Player player) {
		// Check if the player is a player, and if he has permissions
		if (player == null) {
			// The console can use this
			return true;
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
