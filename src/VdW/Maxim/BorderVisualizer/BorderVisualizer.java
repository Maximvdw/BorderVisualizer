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
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.palmergames.bukkit.towny.Towny;

import VdW.Maxim.BorderVisualizer.CommandListener.CommandListener_General;
import VdW.Maxim.BorderVisualizer.Configuration.Config;
import VdW.Maxim.BorderVisualizer.Metrics.Metrics;
import VdW.Maxim.BorderVisualizer.PlayerListener.PlayerListener_Movement;
import VdW.Maxim.BorderVisualizer.PlayerListener.PlayerListener_Quit;

public class BorderVisualizer extends JavaPlugin {
	// Allow other classes to reach this class
	public BorderVisualizer plugin = this;
	
	// Lists to remember if players have used BorderVisualizer
	public static ArrayList<Player> bv_players_chunk = 
			new ArrayList<Player>(); // Stores players that used it
	public static ArrayList<Location> bv_locations_chunk = 
			new ArrayList<Location>(); // Stores the location when used
	public static ArrayList<Player> bv_players_towny_townblock = 
			new ArrayList<Player>(); // Stores players that used it
	public static ArrayList<Location> bv_locations_towny_townblock = 
			new ArrayList<Location>(); // Stores the location when used
	public static ArrayList<Player> bv_players_towny_town = 
			new ArrayList<Player>(); // Stores players that used it
	public static ArrayList<String> bv_townName_towny_town = 
			new ArrayList<String>(); // Stores the location when used
	public static ArrayList<Location> bv_locations_towny_town = 
			new ArrayList<Location>(); // Stores the location when used
	public static ArrayList<Player> bv_players_worldguard_region = 
			new ArrayList<Player>(); // Stores players that used it
	public static ArrayList<String> bv_regionName_worldguard_region = 
			new ArrayList<String>(); // Stores the location when used
	public static ArrayList<Location> bv_locations_worldguard_region = 
			new ArrayList<Location>(); // Stores the location when used

	// Load other plugins
	public static WorldGuardPlugin WorldGuard;
	public static Towny Towny;
	
	// Listeners
	private CommandListener_General Commands_GENERAL;
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
		try{
			Plugin p = pm.getPlugin("WorldGuard");
			if (p!=null)
			{
				WorldGuard = (WorldGuardPlugin)p;	
				// Display Hook message
				getServer().getLogger().info("[WorldGuard] Hooked into BorderVisualizer!");
			}
		}catch (Exception ex){
		}
		
		// Load Towny plugin if available
		try{
			Plugin p = pm.getPlugin("Towny");
			if (p!=null)
			{
				Towny = (Towny)p;
				// Display Hook message
				getServer().getLogger().info("[Towny] Hooked into BorderVisualizer!");
			}
		}catch (Exception ex){
		}
		
		// Load General command listener
		try{
			Commands_GENERAL = new CommandListener_General(this);
			getCommand("bv").setExecutor(Commands_GENERAL);	
		}catch (Exception ex){
		}
		
		// Load Player movement listener
		if (Config.allowPlayerMoveEvent)
		{
			try{
				PlayerListener_MOVEMENT = new PlayerListener_Movement(this);
				pm.registerEvents(PlayerListener_MOVEMENT, this);
			}catch (Exception ex){
			}
		}
		
		// Load Player quit listener
		try{
			PlayerListener_QUIT = new PlayerListener_Quit(this);
			pm.registerEvents(PlayerListener_QUIT, this);
		}catch (Exception ex){
		}
	}

	public void onDisable() {

	}
}
