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
import org.bukkit.World;
import org.bukkit.entity.Player;

import VdW.Maxim.BorderVisualizer.BorderVisualizer;
import VdW.Maxim.BorderVisualizer.Configuration.Config;
import VdW.Maxim.BorderVisualizer.DataStore.SaveData;
import VdW.Maxim.BorderVisualizer.GenerateView.View2DSquare;
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

	public void visualize(Player player,String viewName,int viewType, String displayName) {
		/* DEBUG LOGGING */
		if (Config.debugMode == true) {
			SendConsole.info("EXEC: TOWNY_TOWN");
			SendConsole.info("Starting VisualizePlayer:" + player.getName());
		}

		// Save the player's location
		Location location = player.getLocation();

		// Get the world of the player
		World world = player.getWorld();
		// Get the town of the player location
		TownBlock current_TownBlock = TownyUniverse.getTownBlock(location);
		Town town = null;
		try {
			if (displayName != null)
			{
				town = TownyUniverse.getDataSource().getTown(displayName);
			}
			else
			{
				town = current_TownBlock.getTown();
			}
		} catch (Exception ex) {
		}

		// Check if town exist
		if (town != null) {
			// Get all the townblocks
			List<TownBlock> blocks = town.getTownBlocks();
			View2DSquare[] squareBlocks = new View2DSquare[blocks.size()];
			// Array with all townblock data
			int i = 0;
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
				
				// Save data
				View2DSquare townBlockView = new View2DSquare();
				townBlockView.addData(x, z, size, ignore);
				squareBlocks[i] = townBlockView;
				i += 1;
			}
			
			// Save the Square Block set
			SaveData data = new SaveData(plugin);
			data.save2DSquareSet(player, viewName, viewType, squareBlocks);
		} else {
			// Send message
			SendGame.sendMessage(Messages.error_nolocation.replace("{VIEW}", "town"), player);
		}
	}
}
