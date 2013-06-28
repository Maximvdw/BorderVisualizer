/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 31/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package vdw.maxim.bordervisualizer.userinterface;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SendGame {
	// Send a private message
	public static void sendMessage(String message, Player player) {
		// Replace color characters in message
		String msg = chatColor.ToGame(message);
		// Send the message
		player.sendMessage(msg);
	}

	// Broadcast a message
	public static void broadcastMessage(String message) {
		// Replace color characters in message
		String msg = chatColor.ToGame(message);
		// Send the message
		Bukkit.broadcastMessage(msg);
	}
}
