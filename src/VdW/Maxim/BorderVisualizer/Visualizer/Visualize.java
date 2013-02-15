/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 30/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer.Visualizer;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import VdW.Maxim.BorderVisualizer.BorderVisualizer;
import VdW.Maxim.BorderVisualizer.Configuration.Config;
import VdW.Maxim.BorderVisualizer.DataStore.LoadData;
import VdW.Maxim.BorderVisualizer.DataStore.ResetData;
import VdW.Maxim.BorderVisualizer.GenerateView.Generate_2D_Square;
import VdW.Maxim.BorderVisualizer.GenerateView.Generate_2D_SquareFrame;
import VdW.Maxim.BorderVisualizer.GenerateView.ViewObjects;
import VdW.Maxim.BorderVisualizer.GenerateView.ViewTypes;
import VdW.Maxim.BorderVisualizer.Locale.Messages;
import VdW.Maxim.BorderVisualizer.UserInterface.SendConsole;
import VdW.Maxim.BorderVisualizer.UserInterface.SendGame;

public class Visualize {

	// Load the arguments
	public static void createVisualize(BorderVisualizer plugin,Player player, String viewName, int viewType, String displayName, String pluginName) {
		// Let the plugin load the data
		PluginManager pm = Bukkit.getPluginManager();
		BorderVisualizer bv_plugin = (BorderVisualizer) pm.getPlugin(pluginName);
		bv_plugin.visualize(player, viewName, viewType, displayName);
		
		// Load data from datastore
		LoadData data = new LoadData(plugin);
		int viewObject = data.getViewObject(player);
		
		// Draw the shape based on the gathered data
		if (viewObject == ViewObjects._2D_SQUARE)
		{
			// Load data
			int xpos = data.get2D_SQUARE_Xpos(player);
			int zpos = data.get2D_SQUARE_Zpos(player);
			int size = data.get2D_SQUARE_size(player);
			boolean[] ignore = data.get2D_SQUARE_Ignore(player);
			
			// Config data
			Material block = Material.GLASS;
			int ystart = 0;
			int height = (int) (player.getLocation().getY() + 20);
			
			// Generate the view
			if (viewType == ViewTypes.VIEW_GLASS_WALL)
			{
				// Generate the 2D Walls
				Generate_2D_Square generator = new Generate_2D_Square(plugin);
				generator.generate(player, xpos,ystart, zpos, size, height, block,
						ignore);
			}
			else if (viewType == ViewTypes.VIEW_GLASS_FRAME)
			{
				// Generate the 2D Walls
				Generate_2D_SquareFrame generator = new Generate_2D_SquareFrame(plugin);
				generator.generate(player, xpos,ystart, zpos, size, height, block,
						ignore);
			}

			// Send Message
			SendGame.sendMessage(
					Messages.config_visualized.replace("{VIEW}", viewName), player);
		}else if (viewObject == ViewObjects._2D_SQUARESET)
		{
			// Load data
			int[] xpos = data.get2D_SQUARESET_Xpos(player);
			int[] zpos = data.get2D_SQUARESET_Zpos(player);
			int[] size = data.get2D_SQUARESET_size(player);
			ArrayList<boolean[]> ignore = data.get2D_SQUARESET_Ignore(player);
			
			for (int i = 0;i<xpos.length;i++)
			{
				/* DEBUG LOGGING */
				if (Config.debugMode == true) {
					SendConsole.info("Loaded information for 2D_SQUARESET");
					SendConsole.info("Data X: " + xpos[i]);
					SendConsole.info("Data Z: " + zpos[i]);
				}
				
				// Config data
				Material block = Material.GLASS;
				int ystart = 0;
				int height = (int) (player.getLocation().getY() + 20);
				
				// Generate the view
				if (viewType == ViewTypes.VIEW_GLASS_WALL)
				{
					// Generate the 2D Walls
					Generate_2D_Square generator = new Generate_2D_Square(plugin);
					generator.generate(player, xpos[i],ystart, zpos[i], size[i], height, block,
							ignore.get(i));
				}
				else if (viewType == ViewTypes.VIEW_GLASS_FRAME)
				{
					// Generate the 2D Walls
					Generate_2D_SquareFrame generator = new Generate_2D_SquareFrame(plugin);
					generator.generate(player, xpos[i],ystart, zpos[i], size[i], height, block,
							ignore.get(i));
				}
			}
			
			// Send Message
			SendGame.sendMessage(
					Messages.config_visualized.replace("{VIEW}", viewName), player);
		}
	}
	
	public static void deleteVisualize(BorderVisualizer plugin, Player player) {
		// Load data from datastore
		LoadData data = new LoadData(plugin);
		int viewObject = data.getViewObject(player);
		int viewType = data.getViewType(player);
		String viewName = data.getViewName(player);
		
		// Draw the shape based on the gathered data
		if (viewObject == ViewObjects._2D_SQUARE)
		{
			// Load data
			int xpos = data.get2D_SQUARE_Xpos(player);
			int zpos = data.get2D_SQUARE_Zpos(player);
			int size = data.get2D_SQUARE_size(player);
			boolean[] ignore = data.get2D_SQUARE_Ignore(player);
			
			// Config data
			Material block = Material.AIR;
			int ystart = 0;
			int height = (int) (player.getLocation().getY() + 20);
			
			// Generate the view
			if (viewType == ViewTypes.VIEW_GLASS_WALL)
			{
				// Generate the 2D Walls
				Generate_2D_Square generator = new Generate_2D_Square(plugin);
				generator.generate(player, xpos,ystart, zpos, size, height, block,
						ignore);
			}
			else if (viewType == ViewTypes.VIEW_GLASS_FRAME)
			{
				// Generate the 2D Walls
				Generate_2D_SquareFrame generator = new Generate_2D_SquareFrame(plugin);
				generator.generate(player, xpos,ystart, zpos, size, height, block,
						ignore);
			}

			// Send Message
			SendGame.sendMessage(
					Messages.config_visualized.replace("{VIEW}", viewName), player);
		}else if (viewObject == ViewObjects._2D_SQUARESET)
		{
			// Load data
			int[] xpos = data.get2D_SQUARESET_Xpos(player);
			int[] zpos = data.get2D_SQUARESET_Zpos(player);
			int[] size = data.get2D_SQUARESET_size(player);
			ArrayList<boolean[]> ignore = data.get2D_SQUARESET_Ignore(player);
			
			for (int i = 0;i<xpos.length;i++)
			{
				/* DEBUG LOGGING */
				if (Config.debugMode == true) {
					SendConsole.info("Loaded information for 2D_SQUARESET");
					SendConsole.info("Data X: " + xpos[i]);
					SendConsole.info("Data Z: " + zpos[i]);
				}
				
				// Config data
				Material block = Material.AIR;
				int ystart = 0;
				int height = (int) (player.getLocation().getY() + 20);
				
				// Generate the view
				if (viewType == ViewTypes.VIEW_GLASS_WALL)
				{
					// Generate the 2D Walls
					Generate_2D_Square generator = new Generate_2D_Square(plugin);
					generator.generate(player, xpos[i],ystart, zpos[i], size[i], height, block,
							ignore.get(i));
				}
				else if (viewType == ViewTypes.VIEW_GLASS_FRAME)
				{
					// Generate the 2D Walls
					Generate_2D_SquareFrame generator = new Generate_2D_SquareFrame(plugin);
					generator.generate(player, xpos[i],ystart, zpos[i], size[i], height, block,
							ignore.get(i));
				}
			}
		}
		
		// Delete all data
		ResetData reset = new ResetData(plugin);
		reset.resetAll(player);
	}
}
