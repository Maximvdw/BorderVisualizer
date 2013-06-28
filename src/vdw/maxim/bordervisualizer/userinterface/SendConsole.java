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
import org.bukkit.plugin.PluginDescriptionFile;

import vdw.maxim.bordervisualizer.configuration.Config;


public class SendConsole {
	/* Send INFO Console message */
	public static void info(String message) {
		// Get plugin information
		PluginDescriptionFile pluginInfo = Bukkit.getPluginManager()
				.getPlugin("BorderVisualizer").getDescription();
		String plugin_prefix = "[" + pluginInfo.getName() + "] ";

		// Replace color characters in message
		String msg = message;
		if (Config.allowConsoleColor == true) {
			// Add colors
			msg = chatColor.ToConsole(plugin_prefix + message);
		} else {
			// Remove colors
			msg = chatColor.ToRemove(plugin_prefix + message);
		}
		
		// Send the message
		Bukkit.getServer().getLogger().info(msg);
	}

	/* Send WARNING Console message */
	public static void warning(String message) {
		// Get plugin information
		PluginDescriptionFile pluginInfo = Bukkit.getPluginManager()
				.getPlugin("BorderVisualizer").getDescription();
		String plugin_prefix = "[" + pluginInfo.getName() + "] ";

		// Replace color characters in message
		String msg = message;
		if (Config.allowConsoleColor == true) {
			// Add colors
			msg = chatColor.ToConsole(plugin_prefix + message);
		} else {
			// Remove colors
			msg = chatColor.ToRemove(plugin_prefix + message);
		}
		
		// Send the message
		Bukkit.getServer().getLogger().warning(msg);
	}

	/* Send SEVERE Console message */
	public static void severe(String message) {
		// Get plugin information
		PluginDescriptionFile pluginInfo = Bukkit.getPluginManager()
				.getPlugin("BorderVisualizer").getDescription();
		String plugin_prefix = "[" + pluginInfo.getName() + "] ";

		// Replace color characters in message
		String msg = message;
		if (Config.allowConsoleColor == true) {
			// Add colors
			msg = chatColor.ToConsole(plugin_prefix + message);
		} else {
			// Remove colors
			msg = chatColor.ToRemove(plugin_prefix + message);
		}
		
		// Send the message
		Bukkit.getServer().getLogger().severe(msg);
	}
}
