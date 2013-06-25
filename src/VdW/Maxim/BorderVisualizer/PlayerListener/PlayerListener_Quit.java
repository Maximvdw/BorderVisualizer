/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 31/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer.PlayerListener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import VdW.Maxim.BorderVisualizer.BorderVisualizer;
import VdW.Maxim.BorderVisualizer.DataStore.LoadData;
import VdW.Maxim.BorderVisualizer.DataStore.ResetData;
import VdW.Maxim.BorderVisualizer.DataStore.dataPlayers;
import VdW.Maxim.BorderVisualizer.Locale.Messages;
import VdW.Maxim.BorderVisualizer.UserInterface.SendConsole;

public class PlayerListener_Quit implements Listener {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public PlayerListener_Quit(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	/* Delete the player from the array when he leaves */
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerQuit(PlayerQuitEvent e) {
		// Get Player name
		Player player = e.getPlayer();

		// Check if the player is in any list
		if (dataPlayers.contains(player)) {
			// Show message
			LoadData data = new LoadData(plugin);
			SendConsole.warning(Messages.warning_player_quit.replace(
					"{PLAYER}", player.getName()).replace("{LIST}",
					data.getViewName(player)));
			// Reset view
			ResetData reset = new ResetData(plugin);
			reset.resetAll(player);
		}
	}
}
