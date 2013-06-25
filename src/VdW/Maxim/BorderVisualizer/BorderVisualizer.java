/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 25/06/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.palmergames.bukkit.towny.Towny;

import VdW.Maxim.BorderVisualizer.CommandListener.CommandListener;
import VdW.Maxim.BorderVisualizer.Configuration.BorderVisualizerConfiguration;
import VdW.Maxim.BorderVisualizer.Configuration.Config;
import VdW.Maxim.BorderVisualizer.Metrics.Metrics;
import VdW.Maxim.BorderVisualizer.PlayerListener.PlayerListener_Movement;
import VdW.Maxim.BorderVisualizer.PlayerListener.PlayerListener_Quit;
import VdW.Maxim.BorderVisualizer.UserInterface.SendConsole;

public class BorderVisualizer extends JavaPlugin {
	// Allow other classes to reach this class
	public BorderVisualizer plugin = this;

	// Load other plugins
	public static WorldGuardPlugin WorldGuard;
	public static Towny Towny;

	// Listeners
	private PlayerListener_Movement PlayerListener_MOVEMENT;
	private PlayerListener_Quit PlayerListener_QUIT;

	public void onEnable() {
		// Show Message
		SendConsole.info("Initializing ...");

		// Load settings
		SendConsole.info("Loading configuration ...");
		try{
			BorderVisualizerConfiguration config = new BorderVisualizerConfiguration(plugin);
			config.firstRun();
			config.loadYamls();
		}catch(Exception ex){
			// Critical error
			SendConsole.severe("Could not load the configuration!");
			SendConsole.severe("Using default configuration!");
		}
		try {
			Config.enabled = BorderVisualizerConfiguration.config
					.getBoolean("enabled");
		} catch (Exception ex) {
			// Could not load that setting
		}
		try {
			Config.allowOpPermission = BorderVisualizerConfiguration.config
					.getBoolean("allowOpPermissions");
		} catch (Exception ex) {
			// Could not load that setting
		}
		try {
			Config.allowConsoleColor = BorderVisualizerConfiguration.config
					.getBoolean("allowConsoleColor");
		} catch (Exception ex) {
			// Could not load that setting
		}
		try {
			Config.allowPlayerMoveEvent = BorderVisualizerConfiguration.config
					.getBoolean("allowPlayerMoveEvent");
		} catch (Exception ex) {
			// Could not load that setting
		}
		try {
			Config.allowAuthorPermissions = BorderVisualizerConfiguration.config
					.getBoolean("allowAuthorPermissions");
		} catch (Exception ex) {
			// Could not load that setting
		}
		try {
			Config.allowAllCommands = BorderVisualizerConfiguration.config
					.getBoolean("allowAllCommands");
		} catch (Exception ex) {
			// Could not load that setting
		}
		try {
			Config.debugMode = BorderVisualizerConfiguration.config
					.getBoolean("debugMode");
		} catch (Exception ex) {
			// Could not load that setting
		}
		try {
			Config.blockID = BorderVisualizerConfiguration.config
					.getInt("blockID");
		} catch (Exception ex) {
			// Could not load that setting
		}
		try {
			Config.blockHeight = BorderVisualizerConfiguration.config
					.getInt("blockHeight");
		} catch (Exception ex) {
			// Could not load that setting
		}
		SendConsole.info("Configuration loaded!");
		

		// Define the plugin Manager
		PluginManager pm = Bukkit.getServer().getPluginManager();

		// Start Metrics
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
		}

		// Load WorldGuard plugin if available
		try {
			Plugin p = pm.getPlugin("WorldGuard");
			if (p != null) {
				WorldGuard = (WorldGuardPlugin) p;
				// Display Hook message
				getServer().getLogger().info(
						"[WorldGuard] Hooked into BorderVisualizer!");
			}
		} catch (Exception ex) {
		}

		// Load Towny plugin if available
		try {
			Plugin p = pm.getPlugin("Towny");
			if (p != null) {
				Towny = (Towny) p;
				// Display Hook message
				getServer().getLogger().info(
						"[Towny] Hooked into BorderVisualizer!");
			}
		} catch (Exception ex) {
		}

		// Load Player movement listener
		if (Config.allowPlayerMoveEvent) {
			try {
				PlayerListener_MOVEMENT = new PlayerListener_Movement(this);
				pm.registerEvents(PlayerListener_MOVEMENT, this);
			} catch (Exception ex) {
			}
		}

		// Load Player quit listener
		try {
			PlayerListener_QUIT = new PlayerListener_Quit(this);
			pm.registerEvents(PlayerListener_QUIT, this);
		} catch (Exception ex) {
		}
	}

	public void onDisable() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		// Let the CommandListener execute the command
		CommandListener cmdExec = new CommandListener(plugin);
		return cmdExec.onCommand(sender, command, label, args);
	}
}
