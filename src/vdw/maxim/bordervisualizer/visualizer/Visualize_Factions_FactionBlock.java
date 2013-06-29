/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 30/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package vdw.maxim.bordervisualizer.visualizer;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import vdw.maxim.bordervisualizer.BorderVisualizer;
import vdw.maxim.bordervisualizer.configuration.Config;
import vdw.maxim.bordervisualizer.userinterface.SendConsole;



import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.FactionColls;

public class Visualize_Factions_FactionBlock {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Visualize_Factions_FactionBlock(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	public void visualize(Player player, Boolean allowMove, String viewName,
			int viewType, String displayName) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: Factions_FactionBLOCK");
			SendConsole.info("Starting VisualizePlayer:" + player.getName());
		}

		// Save the player's location
		Location location = player.getLocation();

		// Get all factions
		FactionColl fc = FactionColls.get().get(
				plugin.getServer().getConsoleSender());
		
		// Get the faction
		Faction f = new Faction();
		
		// Get the faction by name
		if (displayName != null)
		{
			f = fc.getByName(displayName);
		}else{
	
		}
		
		// Check if a faction has been found
		if (f != null){
			
		}
	}
}
