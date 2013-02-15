/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 31/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.palmergames.bukkit.towny.Towny;

import VdW.Maxim.BorderVisualizer.CommandListener.CommandListener;
import VdW.Maxim.BorderVisualizer.Configuration.BorderVisualizerConfiguration;
import VdW.Maxim.BorderVisualizer.Configuration.Config;
import VdW.Maxim.BorderVisualizer.DataStore.SaveData;
import VdW.Maxim.BorderVisualizer.Metrics.Metrics;
import VdW.Maxim.BorderVisualizer.PlayerListener.PlayerListener_Movement;
import VdW.Maxim.BorderVisualizer.PlayerListener.PlayerListener_Quit;
import VdW.Maxim.BorderVisualizer.RegisterView.RegisterView;
import VdW.Maxim.BorderVisualizer.Visualizer.Visualize_Chunk;
import VdW.Maxim.BorderVisualizer.Visualizer.Visualize_Towny_Town;

public class BorderVisualizer extends JavaPlugin {
	// Allow other classes to reach this class
	public BorderVisualizer plugin = this;

	// List all views in BorderVisualizer
	public static ArrayList<String> bv_view_name = new ArrayList<String>();
	public static ArrayList<String> bv_view_command = new ArrayList<String>();
	public static ArrayList<String> bv_view_plugin = new ArrayList<String>();
	
	// Load other plugins
	public static WorldGuardPlugin WorldGuard;
	public static Towny Towny;

	// Listeners
	private PlayerListener_Movement PlayerListener_MOVEMENT;
	private PlayerListener_Quit PlayerListener_QUIT;

	public void onEnable() {
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

		// Prepare configuration
		BorderVisualizerConfiguration config = new BorderVisualizerConfiguration(
				plugin);
		try {
			// Check if files exist
			config.firstRun();
		} catch (Exception ex) {
		}
		
		RegisterView.add("Chunk", "chunk", "BorderVisualizer");
		RegisterView.add("Town Block", "townblock", "BorderVisualizer");
		RegisterView.add("Town", "town", "BorderVisualizer");
		RegisterView.add("Region", "region", "BorderVisualizer");
	}

	public void onDisable() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		// Let the CommandListener execute the command
		CommandListener cmdExec = new CommandListener(
				plugin);
		return cmdExec.onCommand(sender, command,label, args);
	}
	
	/* BorderVisualizer Hook | There functions get executed on player view */
	public boolean visualize(Player player,String viewName,int viewType, String displayName)
	{
		Visualize_Towny_Town visualizer = new Visualize_Towny_Town(plugin);
		visualizer.visualize(player, viewName, viewType, displayName);
		return true;
	}
}
