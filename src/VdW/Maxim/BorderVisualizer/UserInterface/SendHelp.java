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

public class SendHelp {
	// Send general help
	public static void help_general(String message, Player player) {
		// Replace color characters in message
		String msg = chatColor.ToGame(message);
		// Send the message
		player.sendMessage(msg);
	}
}
