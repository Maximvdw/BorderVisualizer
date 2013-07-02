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
import org.bukkit.World;
import org.bukkit.entity.Player;

import vdw.maxim.bordervisualizer.BorderVisualizer;
import vdw.maxim.bordervisualizer.configuration.Config;
import vdw.maxim.bordervisualizer.datastore.SaveData;
import vdw.maxim.bordervisualizer.generateview.View2DSquare;
import vdw.maxim.bordervisualizer.locale.Messages;
import vdw.maxim.bordervisualizer.userinterface.SendConsole;
import vdw.maxim.bordervisualizer.userinterface.SendGame;

import com.massivecraft.factions.entity.BoardColls;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.FactionColls;
import com.massivecraft.mcore.ps.PS;

public class Visualize_Factions_Faction {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Visualize_Factions_Faction(BorderVisualizer plugin) {
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

		// Get the world of the player
		World world = player.getWorld();

		// Get all factions
		FactionColl fc = FactionColls.get().get(
				plugin.getServer().getConsoleSender());

		// Get the faction
		Faction f = null;

		// Get the faction
		if (displayName != null) {
			f = fc.getByName(displayName);
		} else {
			f = BoardColls.get().getFactionAt(PS.valueOf(location));
		}

		// Check if a faction has been found
		if (!f.isNone()) {
			View2DSquare[] squareBlocks = new View2DSquare[BoardColls.get()
					.getChunks(f).size()];
			int i = 0;
			for (PS c : BoardColls.get().getChunks(f)) {
				// Get the size of a townblock
				int size = 16;
				// Check if there are townblocks next to it
				boolean[] ignore = new boolean[4];
				if (!(BoardColls.get().getFactionAt(PS.valueOf(new Location(
						world, (c.getChunkX() + 1) * size, 0, (c.getChunkZ())
								* size)))).isNone()) {
					ignore[0] = true;
				}
				if (!(BoardColls.get().getFactionAt(PS.valueOf(new Location(
						world, (c.getChunkX()) * size, 0, (c.getChunkZ() + 1)
								* size)))).isNone()) {
					ignore[1] = true;
				}
				if (!(BoardColls.get().getFactionAt(PS.valueOf(new Location(
						world, (c.getChunkX() - 1) * size, 0, (c.getChunkZ())
								* size)))).isNone()) {
					ignore[2] = true;
				}
				if (!(BoardColls.get().getFactionAt(PS.valueOf(new Location(
						world, (c.getChunkX()) * size, 0, (c.getChunkZ() - 1)
								* size)))).isNone()) {
					ignore[3] = true;
				}

				// Get location of the townblock
				int x = size * c.getChunkX();
				int z = size * c.getChunkZ();

				// Save data
				View2DSquare townBlockView = new View2DSquare();
				townBlockView.addData(x, z, size, ignore);
				squareBlocks[i] = townBlockView;
				i += 1;
			}

			// Save the Square Block set
			SaveData data = new SaveData(plugin);
			data.save2DSquareSet(player, allowMove, viewName, viewType,
					squareBlocks);
		} else {
			// Send message
			SendGame.sendMessage(
					Messages.error_nolocation.replace("{VIEW}", "faction"),
					player);
			return; // No faction
		}
	}
}
