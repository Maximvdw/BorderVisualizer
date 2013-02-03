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
import VdW.Maxim.BorderVisualizer.GenerateView.Generate_2D_Walls;
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
		BorderVisualizer.bv_townName_towny_town.add("");
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
		if (town != null) {
			// Get all the townblocks
			List<TownBlock> blocks = town.getTownBlocks();
			for (TownBlock townBlock : blocks) {
				// Get the size of a townblock
				int size = Coord.getCellSize();
				// Check if there are townblocks next to it
				boolean[] ignore = new boolean[4];
				if (TownyUniverse.getTownBlock(new Location(world, (townBlock
						.getX() + 1) * size, 0, (townBlock.getZ()) * size)) != null) {
					ignore[0] = true;
				}
				if (TownyUniverse.getTownBlock(new Location(world, (townBlock
						.getX()) * size, 0, (townBlock.getZ() + 1) * size)) != null) {
					ignore[1] = true;
				}
				if (TownyUniverse.getTownBlock(new Location(world, (townBlock
						.getX() - 1) * size, 0, (townBlock.getZ()) * size)) != null) {
					ignore[2] = true;
				}
				if (TownyUniverse.getTownBlock(new Location(world, (townBlock
						.getX()) * size, 0, (townBlock.getZ() - 1) * size)) != null) {
					ignore[3] = true;
				}

				// Get location of the townblock
				int x = size * townBlock.getX();
				int z = size * townBlock.getZ();

				/* DEBUG LOGGING */
				if (Config.debugMode == true) {
					SendConsole.info("Visualization data: TownBlockSize="
							+ size);
					SendConsole.info("Visualization data: Block_x=" + x);
					SendConsole.info("Visualization data: Block_z=" + z);
					SendConsole.info("Visualization data: TownBlock_x="
							+ townBlock.getX());
					SendConsole.info("Visualization data: TownBlock_z="
							+ townBlock.getZ());
				}

				// Get the size and y position to show blocks
				int height = (int)location.getY() + 20;
				int y = 0;
				Material block = Material.GLASS;

				// Generate the 2D Walls
				Generate_2D_Walls generator = new Generate_2D_Walls(plugin);
				generator.generate_square(player, x,y, z, size, height, block,
						ignore);
			}
			// Send Message
			SendGame.sendMessage(
					Messages.config_visualized.replace("{VIEW}", "Town"), player);
		} else {
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
		String townName = BorderVisualizer.bv_townName_towny_town.get(index);
		Location location = (Location) BorderVisualizer.bv_locations_towny_town
				.get(index);

		// Check if the player asked for 'A new region visualization'
		if ((nextView != null) && (townName != nextView)) {
			SendGame.sendMessage(
					Messages.warning_nextview.replace("{VIEW}", "Town"), player);
		}

		// Remove the saved data
		BorderVisualizer.bv_players_towny_town.remove(player);
		BorderVisualizer.bv_locations_towny_town.remove(index);
		BorderVisualizer.bv_townName_towny_town.remove(index);
		
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
			int size = Coord.getCellSize();
			
			// Check if there are townblocks next to it
			boolean[] ignore = new boolean[4];
			if (TownyUniverse.getTownBlock(new Location(world, (townBlock
					.getX() + 1) * size, 0, (townBlock.getZ()) * size)) != null) {
				ignore[0] = true;
			}
			if (TownyUniverse.getTownBlock(new Location(world, (townBlock
					.getX()) * size, 0, (townBlock.getZ() + 1) * size)) != null) {
				ignore[1] = true;
			}
			if (TownyUniverse.getTownBlock(new Location(world, (townBlock
					.getX() - 1) * size, 0, (townBlock.getZ()) * size)) != null) {
				ignore[2] = true;
			}
			if (TownyUniverse.getTownBlock(new Location(world, (townBlock
					.getX()) * size, 0, (townBlock.getZ() - 1) * size)) != null) {
				ignore[3] = true;
			}

			// Get location of the townblock
			int x = size * townBlock.getX();
			int z = size * townBlock.getZ();

			/* DEBUG LOGGING */
			if (Config.debugMode == true) {
				SendConsole.info("Visualization data: TownBlockSize="
						+ size);
				SendConsole.info("Visualization data: Block_x=" + x);
				SendConsole.info("Visualization data: Block_z=" + z);
				SendConsole.info("Visualization data: TownBlock_x="
						+ townBlock.getX());
				SendConsole.info("Visualization data: TownBlock_z="
						+ townBlock.getZ());
			}

			// Get the size and y position to show blocks
			int height = (int)location.getY() + 20;
			int y = 0;
			Material block = Material.AIR;

			// Generate the 2D Walls
			Generate_2D_Walls generator = new Generate_2D_Walls(plugin);
			generator.generate_square(player, x,y, z, size, height, block,
					ignore);
		}
	}
}
