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
import org.bukkit.entity.Player;

import VdW.Maxim.BorderVisualizer.BorderVisualizer;
import VdW.Maxim.BorderVisualizer.Configuration.Config;
import VdW.Maxim.BorderVisualizer.GenerateView.Generate_2D_Walls;
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
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: TOWNY_TOWNBLOCK");
			SendConsole.info("Starting VisualizePlayer:" + player.getName()
					+ ";" + blockid);
		}

		// Save the player's location
		Location location = player.getLocation();
		BorderVisualizer.bv_players_towny_townblock.add(player);
		BorderVisualizer.bv_locations_towny_townblock.add(location);

		// Get the current townblock
		TownBlock townBlock = TownyUniverse.getTownBlock(location);
		// Check if townBlock exist
		if (townBlock != null) {
			// Get the size of a townblock
			int size = Coord.getCellSize();
			// Get location of the townblock
			int x = size * townBlock.getX();
			int z = size * townBlock.getZ();

			/* DEBUG LOGGING */
			if (Config.debugMode == true) {
				SendConsole.info("Visualization data: TownBlockSize=" + size);
				SendConsole.info("Visualization data: Block_x=" + x);
				SendConsole.info("Visualization data: Block_z=" + z);
				SendConsole.info("Visualization data: TownBlock_x="
						+ townBlock.getX());
				SendConsole.info("Visualization data: TownBlock_z="
						+ townBlock.getZ());
			}
			Material block = Material.GLASS; // Block to replace it with
			
			// Get the size and y position to show blocks
			int height = (int)location.getY() + 20;
			int y = 0;
			
			// Generate the 2D Walls
			Generate_2D_Walls generator = new Generate_2D_Walls(plugin);
			generator.generate_square(player, x,y, z, size, height, block, null);
			
			// Send Message
			SendGame.sendMessage(
					Messages.config_visualized.replace("{VIEW}", "Townblock"), player);
		} else {
			// Remove the saved data
			int index = BorderVisualizer.bv_players_towny_townblock
					.indexOf(player);
			BorderVisualizer.bv_players_towny_townblock.remove(player);
			BorderVisualizer.bv_locations_towny_townblock.remove(index);
			// Send error message
			SendGame.sendMessage(Messages.error_towny_townblock_nolocation,
					player);
		}
	}

	public void remove_player(Player player) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: TOWNY_TOWNBLOCK");
			SendConsole.info("Starting RemovePlayer:" + player.getName());
		}

		// Get the players location before executing the command
		int index = BorderVisualizer.bv_players_towny_townblock.indexOf(player);
		Location location = (Location) BorderVisualizer.bv_locations_towny_townblock
				.get(index);

		// Remove the saved data
		BorderVisualizer.bv_players_towny_townblock.remove(player);
		BorderVisualizer.bv_locations_towny_townblock.remove(index);

		// Get the current townblock
		TownBlock townBlock = TownyUniverse.getTownBlock(location);
		// Get the size of a townblock
		int size = Coord.getCellSize();
		// Get location of the townblock
		int x = size * townBlock.getX();
		int z = size * townBlock.getZ();

		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("Visualization data: TownBlockSize=" + size);
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
		
		Material block = Material.AIR; // Block to replace it with

		// Generate the 2D Walls
		Generate_2D_Walls generator = new Generate_2D_Walls(plugin);
		generator.generate_square(player, x,y, z, size, height, block, null);
	}

}
