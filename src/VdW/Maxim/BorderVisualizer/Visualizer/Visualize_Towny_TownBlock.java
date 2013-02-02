/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 30/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer.Visualizer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import VdW.Maxim.BorderVisualizer.BorderVisualizer;
import VdW.Maxim.BorderVisualizer.Configuration.Config;
import VdW.Maxim.BorderVisualizer.Locale.Messages;
import VdW.Maxim.BorderVisualizer.UserInterface.SendConsole;
import VdW.Maxim.BorderVisualizer.UserInterface.SendGame;

import com.palmergames.bukkit.towny.object.Coord;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class Visualize_Towny_TownBlock {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Visualize_Towny_TownBlock(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	public void visualize_player(Player player, int blockid) {
		/* DEBUG LOGGING */
		if (Config.debugMode==true)
		{
			SendConsole.info("EXEC: TOWNY_TOWNBLOCK");
			SendConsole.info("Starting VisualizePlayer:" + player.getName() +
					";" + blockid);
		}
		
		// Save the player's location
		Location location = player.getLocation();
		BorderVisualizer.bv_players_towny_townblock.add(player);
		BorderVisualizer.bv_locations_towny_townblock.add(location);
		
		// Get the world of the player
		World world = player.getWorld();
		// Get the current townblock
		TownBlock townBlock = TownyUniverse.getTownBlock(location);
		// Check if townBlock exist
		if (townBlock != null)
		{
			// Get the size of a townblock
			int townblocksize = Coord.getCellSize();		
			// Define the four corners
			Location corner1;
			Location corner2;
			Location corner3;
			Location corner4;
			// Get location of the townblock
			int x = townblocksize*townBlock.getX();
			int z = townblocksize*townBlock.getZ();
			
			/* DEBUG LOGGING */
			if (Config.debugMode==true)
			{
				SendConsole.info("Visualization data: TownBlockSize=" + townblocksize);
				SendConsole.info("Visualization data: Block_x=" + x);
				SendConsole.info("Visualization data: Block_z=" + z);
				SendConsole.info("Visualization data: TownBlock_x=" + townBlock.getX());
				SendConsole.info("Visualization data: TownBlock_z=" + townBlock.getZ());
			}
		
			townblocksize -= 1;
			int i = 0;
			int j = 0;
			for (i = 0; i < 127; i++)
				for (j = 0; j < townblocksize; j++) {
					corner1 = new Location(world,x+j,i,z);
					corner2 = new Location(world,x+townblocksize,i,j+z);
					corner3 = new Location(world,(x+townblocksize)-j,i,townblocksize+z);
					corner4 = new Location(world,x,i,(z+townblocksize)-j);
					if (corner1.getBlock().getType() == Material.AIR)
						player.sendBlockChange(corner1, Material.GLASS, (byte) 0);
					if (corner2.getBlock().getType() == Material.AIR)
						player.sendBlockChange(corner2, Material.GLASS, (byte) 0);
					if (corner3.getBlock().getType() == Material.AIR)
						player.sendBlockChange(corner3, Material.GLASS, (byte) 0);
					if (corner4.getBlock().getType() == Material.AIR)
						player.sendBlockChange(corner4, Material.GLASS, (byte) 0);
				}
		}
		else
		{
	        // Remove the saved data
			int index = BorderVisualizer.bv_players_towny_townblock.indexOf(player);
	        BorderVisualizer.bv_players_towny_townblock.remove(player);
	        BorderVisualizer.bv_locations_towny_townblock.remove(index);
			// Send error message
			SendGame.sendMessage(Messages.error_towny_townblock_nolocation, player);
		}
	}
	
	public void remove_player(Player player) {
		/* DEBUG LOGGING */
		if (Config.debugMode==true)
		{
			SendConsole.info("EXEC: TOWNY_TOWNBLOCK");
			SendConsole.info("Starting RemovePlayer:" + player.getName());
		}
		
		// Get the players location before executing the command
		int index = BorderVisualizer.bv_players_towny_townblock.indexOf(player);
        Location location = (Location)BorderVisualizer.bv_locations_towny_townblock.get(index);
        
        // Remove the saved data
        BorderVisualizer.bv_players_towny_townblock.remove(player);
        BorderVisualizer.bv_locations_towny_townblock.remove(index);
        
		// Get the world of the player
		World world = location.getWorld();
		// Get the current townblock
		TownBlock townBlock = TownyUniverse.getTownBlock(location);
		// Get the size of a townblock
		int townblocksize = Coord.getCellSize();		
		// Define the four corners
		Location corner1;
		Location corner2;
		Location corner3;
		Location corner4;
		// Get location of the townblock
		int x = townblocksize*townBlock.getX();
		int z = townblocksize*townBlock.getZ();
		
		/* DEBUG LOGGING */
		if (Config.debugMode==true)
		{
			SendConsole.info("Visualization data: TownBlockSize=" + townblocksize);
			SendConsole.info("Visualization data: Block_x=" + x);
			SendConsole.info("Visualization data: Block_z=" + z);
			SendConsole.info("Visualization data: TownBlock_x=" + townBlock.getX());
			SendConsole.info("Visualization data: TownBlock_z=" + townBlock.getZ());
		}
	
		townblocksize -= 1;
		int i = 0;
		int j = 0;
		for (i = 0; i < 127; i++)
			for (j = 0; j < townblocksize; j++) {
				corner1 = new Location(world,x+j,i,z);
				corner2 = new Location(world,x+townblocksize,i,j+z);
				corner3 = new Location(world,(x+townblocksize)-j,i,townblocksize+z);
				corner4 = new Location(world,x,i,(z+townblocksize)-j);
				if (corner1.getBlock().getType() == Material.AIR)
					player.sendBlockChange(corner1, Material.AIR, (byte) 0);
				if (corner2.getBlock().getType() == Material.AIR)
					player.sendBlockChange(corner2, Material.AIR, (byte) 0);
				if (corner3.getBlock().getType() == Material.AIR)
					player.sendBlockChange(corner3, Material.AIR, (byte) 0);
				if (corner4.getBlock().getType() == Material.AIR)
					player.sendBlockChange(corner4, Material.AIR, (byte) 0);
			}
	}

}
