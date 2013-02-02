/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 30/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer.Visualizer;

import java.util.List;

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
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class Visualize_Towny_Town {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Visualize_Towny_Town(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	public void visualize_player(Player player, int blockid) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: TOWNY_TOWN");
			SendConsole.info("Starting VisualizePlayer:" + player.getName()
					+ ";" + blockid);
		}

		// Save the player's location
		Location location = player.getLocation();
		BorderVisualizer.bv_players_towny_town.add(player);
		BorderVisualizer.bv_locations_towny_town.add(location);

		// Get the world of the player
		World world = player.getWorld();
		// Get the town of the player location
		TownBlock current_TownBlock = TownyUniverse.getTownBlock(location);
		Town town = null;
		try {
			town = current_TownBlock.getTown();
		} catch (Exception ex) {
		}

		// Check if town exist
		if (town != null)
		{
			// Get all the townblocks
			List<TownBlock> blocks = town.getTownBlocks();
			for (TownBlock townBlock : blocks) {
				// Get the size of a townblock
				int townblocksize = Coord.getCellSize();
				// Define the four corners
				Location corner1;
				Location corner2;
				Location corner3;
				Location corner4;
				// Check if there are townblocks next to it
				boolean face_x = false;
				boolean face_z = false;
				boolean face_x_neg = false;
				boolean face_z_neg = false;
				if (TownyUniverse.getTownBlock(new Location(world, (townBlock
						.getX() + 1) * townblocksize, 0, (townBlock.getZ()) * townblocksize)) != null) {
					face_x = true;
				}
				if (TownyUniverse.getTownBlock(new Location(world, (townBlock
						.getX()) * townblocksize, 0, (townBlock.getZ() + 1) * townblocksize)) != null) {
					face_z = true;
				}
				if (TownyUniverse.getTownBlock(new Location(world, (townBlock
						.getX() - 1) * townblocksize, 0, (townBlock.getZ()) * townblocksize)) != null) {
					face_x_neg = true;
				}
				if (TownyUniverse.getTownBlock(new Location(world, (townBlock
						.getX()) * townblocksize, 0, (townBlock.getZ() - 1) * townblocksize)) != null) {
					face_z_neg = true;
				}

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
						corner1 = new Location(world, x + j, i, z);
						corner2 = new Location(world, x + townblocksize, i, j + z);
						corner3 = new Location(world, (x + townblocksize) - j, i,townblocksize + z);
						corner4 = new Location(world, x, i, (z + townblocksize) - j);
						if (corner1.getBlock().getType() == Material.AIR
								&& face_z_neg == false)
							player.sendBlockChange(corner1, Material.GLASS,
									(byte) 0);
						if (corner2.getBlock().getType() == Material.AIR
								&& face_x == false)
							player.sendBlockChange(corner2, Material.GLASS,
									(byte) 0);
						if (corner3.getBlock().getType() == Material.AIR
								&& face_z == false)
							player.sendBlockChange(corner3, Material.GLASS,
									(byte) 0);
						if (corner4.getBlock().getType() == Material.AIR
								&& face_x_neg == false)
							player.sendBlockChange(corner4, Material.GLASS,
									(byte) 0);
					}
			}
		}
		else
		{
			// Remove the saved data
			int index = BorderVisualizer.bv_players_towny_town.indexOf(player);
			BorderVisualizer.bv_players_towny_town.remove(player);
			BorderVisualizer.bv_locations_towny_town.remove(index);
			// Send message
			SendGame.sendMessage(Messages.error_towny_town_nolocation, player);
		}
	}

	public void remove_player(Player player, String nextView, int blockid) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: TOWNY_TOWN");
			SendConsole.info("Starting RemovePlayer:" + player.getName());
		}
		// Get the players location before executing the command
		int index = BorderVisualizer.bv_players_towny_town.indexOf(player);
		String townName = BorderVisualizer.bv_townName_towny_town
				.get(index);
		Location location = (Location) BorderVisualizer.bv_locations_towny_town
				.get(index);

		// Check if the player asked for 'A new region visualization'
		if ((nextView != null) && (townName != nextView)) {
			SendGame.sendMessage(Messages.warning_nextview.replace("{VIEW}",
					"Town"), player);
		}
		
		// Remove the saved data
		BorderVisualizer.bv_players_towny_town.remove(player);
		BorderVisualizer.bv_locations_towny_town.remove(index);

		// Get the world of the player
		World world = location.getWorld();
		// Get the town of the player location
		TownBlock current_TownBlock = TownyUniverse.getTownBlock(location);
		Town town = null;
		try {
			town = current_TownBlock.getTown();
		} catch (Exception ex) {
		}

		// Get all the townblocks
		List<TownBlock> blocks = town.getTownBlocks();
		for (TownBlock townBlock : blocks) {
			// Get the size of a townblock
			int townblocksize = Coord.getCellSize();
			// Define the four corners
			Location corner1;
			Location corner2;
			Location corner3;
			Location corner4;
			// Check if there are townblocks next to it
			boolean face_x = false;
			boolean face_z = false;
			boolean face_x_neg = false;
			boolean face_z_neg = false;
			if (TownyUniverse.getTownBlock(new Location(world, (townBlock
					.getX() + 1) * 16, 0, (townBlock.getZ()) * 16)) != null) {
				face_x = true;
			}
			if (TownyUniverse.getTownBlock(new Location(world, (townBlock
					.getX()) * 16, 0, (townBlock.getZ() + 1) * 16)) != null) {
				face_z = true;
			}
			if (TownyUniverse.getTownBlock(new Location(world, (townBlock
					.getX() - 1) * 16, 0, (townBlock.getZ()) * 16)) != null) {
				face_x_neg = true;
			}
			if (TownyUniverse.getTownBlock(new Location(world, (townBlock
					.getX()) * 16, 0, (townBlock.getZ() - 1) * 16)) != null) {
				face_z_neg = true;
			}

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
					corner1 = new Location(world, x + j, i, z);
					corner2 = new Location(world, x + townblocksize, i, j + z);
					corner3 = new Location(world, (x + townblocksize) - j, i,
							townblocksize + z);
					corner4 = new Location(world, x, i, (z + townblocksize) - j);
					if (corner1.getBlock().getType() == Material.AIR
							&& face_z_neg == false)
						player.sendBlockChange(corner1, Material.AIR,
								(byte) 0);
					if (corner2.getBlock().getType() == Material.AIR
							&& face_x == false)
						player.sendBlockChange(corner2, Material.AIR,
								(byte) 0);
					if (corner3.getBlock().getType() == Material.AIR
							&& face_z == false)
						player.sendBlockChange(corner3, Material.AIR,
								(byte) 0);
					if (corner4.getBlock().getType() == Material.AIR
							&& face_x_neg == false)
						player.sendBlockChange(corner4, Material.AIR,
								(byte) 0);
				}
		}
	}
}
