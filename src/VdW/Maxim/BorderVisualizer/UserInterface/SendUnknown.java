/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 31/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer.UserInterface;

import org.bukkit.entity.Player;

public class SendUnknown {
	// Send a private message or console message
	public static void sendMessage(String message, Player player) {
		if (player== null)
		{
			// Console message
			SendConsole.info(message); // Always info
		}else{
			// Player messages
			SendGame.sendMessage(message, player);
		}
	}
}
