/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 25/06/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package vdw.maxim.bordervisualizer;

import java.io.File;
import java.io.IOException;

import net.jzx7.regios.RegiosPlugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import vdw.maxim.bordervisualizer.commandlistener.CommandListener;
import vdw.maxim.bordervisualizer.configuration.BorderVisualizerConfiguration;
import vdw.maxim.bordervisualizer.configuration.Config;
import vdw.maxim.bordervisualizer.datastore.LoadData;
import vdw.maxim.bordervisualizer.datastore.ResetData;
import vdw.maxim.bordervisualizer.datastore.dataPlayers;
import vdw.maxim.bordervisualizer.locale.Messages;
import vdw.maxim.bordervisualizer.metrics.Metrics;
import vdw.maxim.bordervisualizer.playerlistener.PlayerListener_Movement;
import vdw.maxim.bordervisualizer.playerlistener.PlayerListener_Quit;
import vdw.maxim.bordervisualizer.userinterface.SendConsole;
import vdw.maxim.bordervisualizer.userinterface.SendGame;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.ryanhamshire.GriefPrevention.GriefPrevention;

import com.massivecraft.factions.Factions;
import com.palmergames.bukkit.towny.Towny;

import com.tommytony.war.War;


public class BorderVisualizer extends JavaPlugin {
	// Allow other classes to reach this class
	public static BorderVisualizer plugin;

	// Load other plugins
	public static WorldGuardPlugin WorldGuard; // WorldGuard plugin
	public static Towny Towny; // Towny plugin
	public static GriefPrevention GriefPrevention; // GriefPrevention plugin
	public static RegiosPlugin Regios; // Regios plugin
	public static Factions Factions; // Factions plugin
	public static War War; // War plugin
	//public static Residence Residence; // Residence plugin
	
	// Configuration
	public static BorderVisualizerConfiguration BVconfig; // BorderVisualizer config

	// Listeners
	private PlayerListener_Movement PlayerListener_MOVEMENT; // Player Move event
	private PlayerListener_Quit PlayerListener_QUIT; // Player Quit event

	// Constant variables
	public static int configVersion = 1; // Latest configuration version
	
	/**
	 * Enable the plugin
	 */
	public void onEnable() {
		// Show Message
		SendConsole.info("Initializing ...");
		// CREDITS
		SendConsole.info("-----------------------------");
		SendConsole.info("BorderVisualizer v." + this.getDescription().getVersion());
		SendConsole.info("(c) Maxim Van de Wynckel 2013");
		SendConsole.info("-----------------------------");
		// CREDITS
		
		plugin = this; // Set plugin
		
		// Load settings
		SendConsole.info("Loading configuration ...");
		try{
			BVconfig = new BorderVisualizerConfiguration(plugin);
			// Init Yaml config file
			BorderVisualizerConfiguration.configFile = 
					new File(getDataFolder(), "config.yml");
			// Init Yaml config store
			BorderVisualizerConfiguration.config = new YamlConfiguration();
			BVconfig.firstRun();
			BVconfig.loadYamls();
		}catch(Exception ex){
			// Critical error
			SendConsole.severe("Could not load the configuration!");
			SendConsole.severe("Using default configuration!");
		}
		
		try {
			int version = BorderVisualizerConfiguration.config
					.getInt("config");
			// Check if latest version
			if (version!=configVersion){
				// Update configuration
				SendConsole.warning("config.yml outdated!");
				BVconfig.update();
				BVconfig.loadYamls();
				SendConsole.warning("config.yml updated to version " + configVersion + "!");
			}
		} catch (Exception ex) {
			// Could not load that setting
		}
		Config.initConfig(); // Load all data
		if (Config.enabled == false){
			return; // Leave plugin
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
		
		// Load GriefPrevention plugin if availabe
		try {
			Plugin p = pm.getPlugin("GriefPrevention");
			if (p != null) {
				GriefPrevention = (GriefPrevention) p;
				// Display Hook message
				getServer().getLogger().info(
						"[GriefPrevention] Hooked into BorderVisualizer!");
			}
		} catch (Exception ex) {
		}
		
		// Load Regios plugin if availabe
		try {
			Plugin p = pm.getPlugin("Regios");
			if (p != null) {
				Regios = (RegiosPlugin) p;
				// Display Hook message
				getServer().getLogger().info(
						"[Regios] Hooked into BorderVisualizer!");
			}
		} catch (Exception ex) {
		}

		// Load Factions plugin if availabe
		try {
			Plugin p = pm.getPlugin("Factions");
			if (p != null) {
				Factions = (Factions) p;
				// Display Hook message
				getServer().getLogger().info(
						"[Factions] Hooked into BorderVisualizer!");
			}
		} catch (Exception ex) {
		}
		
		// Load War plugin if availabe
		try {
			Plugin p = pm.getPlugin("War");
			if (p != null) {
				War = (War) p;
				// Display Hook message
				getServer().getLogger().info(
						"[War] Hooked into BorderVisualizer!");
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

	/**
	 * Disable the plugin
	 */
	public void onDisable() {
		// Show message
		SendConsole.info("Checking for open border views...");
		// Delete all open views
		Player[] players = this.getServer().getOnlinePlayers();
		// Now check every player for open view
		for(Player player : players)
		{
			// Check if on list
			if(dataPlayers.contains(player)){
				// Delete view
				LoadData data = new LoadData(plugin);
				SendConsole.warning(Messages.warning_player_quit.replace(
						"{PLAYER}", player.getName()).replace("{LIST}",
						data.getViewName(player)));
				ResetData reset = new ResetData(plugin);
				reset.resetAll(player);
				SendGame.sendMessage(Messages.warning_forcereload, player);
			}
		}
	}

	@Override
	/**
	 * Perform a command
	 * @author Maxim
	 * @param sender - The sender
	 * @param comamnd - The command
	 * @param label - The command string
	 * @param args - Arguments
	 */
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		// Let the commandlistener execute the command
		CommandListener cmdExec = new CommandListener(plugin);
		return cmdExec.onCommand(sender, command, label, args);
	}
}
