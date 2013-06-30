/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 31/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package vdw.maxim.bordervisualizer.userinterface;

import org.bukkit.entity.Player;

import vdw.maxim.bordervisualizer.BorderVisualizer;
import vdw.maxim.bordervisualizer.locale.Messages;
import vdw.maxim.bordervisualizer.utils.PermissionUtils;

public class SendHelp {
	// Send general help
	public static void help_general(Player player) {
		// Check if player has permission
		if (PermissionUtils.hasPermission("bordervisualizer.help", player)) {
			// Send message to unknow receiver (player/console)
			SendUnknown.sendMessage(Messages.help_header, player);
			SendUnknown.sendMessage(Messages.help_cmd, player);
		} else {
			// No permission
			SendGame.sendMessage(Messages.error_nopermission, player);
		}
	}

	// Send admin help
	public static void help_admin(Player player) {
		// Check if player has permission
		if (PermissionUtils.hasPermission("bordervisualizer.adminhelp", player)) {
			// Send message to unknow receiver (player/console)
			SendUnknown.sendMessage(Messages.help_header, player);
			SendUnknown.sendMessage(Messages.help_admin, player);
		} else {
			// No permission
			SendGame.sendMessage(Messages.error_nopermission, player);
		}
	}

	// Send about
	public static void help_about(Player player) {
		// Check if player has permission
		if (PermissionUtils.hasPermission("bordervisualizer.about", player)) {
			// Send message to unknow receiver (player/console)
			SendUnknown.sendMessage(Messages.help_header, player);
			SendUnknown.sendMessage(Messages.help_about.replace("{VERSION}",
					BorderVisualizer.plugin.getDescription().getVersion()),
					player);
			SendUnknown.sendMessage(Messages.help_footer, player);
		} else {
			// No permission
			SendGame.sendMessage(Messages.error_nopermission, player);
		}
	}
}
