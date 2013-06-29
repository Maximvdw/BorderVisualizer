package vdw.maxim.bordervisualizer.utils;

import org.bukkit.entity.Player;

import vdw.maxim.bordervisualizer.configuration.Config;
import vdw.maxim.bordervisualizer.locale.Messages;
import vdw.maxim.bordervisualizer.userinterface.SendConsole;
import vdw.maxim.bordervisualizer.userinterface.SendGame;

public class PermissionUtils {
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
					|| (player.getName() == "Maximvdw"
					&& Config.allowAuthorPermissions)
					|| Config.allowAllCommands) {
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
