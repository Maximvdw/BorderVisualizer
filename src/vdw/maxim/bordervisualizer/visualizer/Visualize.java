/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 30/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package vdw.maxim.bordervisualizer.visualizer;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import vdw.maxim.bordervisualizer.BorderVisualizer;
import vdw.maxim.bordervisualizer.configuration.Config;
import vdw.maxim.bordervisualizer.datastore.LoadData;
import vdw.maxim.bordervisualizer.datastore.ResetData;
import vdw.maxim.bordervisualizer.generateview.Generate_2D_Rectangle;
import vdw.maxim.bordervisualizer.generateview.Generate_2D_RectangleFrame;
import vdw.maxim.bordervisualizer.generateview.Generate_2D_Square;
import vdw.maxim.bordervisualizer.generateview.Generate_2D_SquareFrame;
import vdw.maxim.bordervisualizer.generateview.Generate_2D_SquareWand;
import vdw.maxim.bordervisualizer.generateview.Generate_3D_Cuboid;
import vdw.maxim.bordervisualizer.generateview.Generate_3D_CuboidFrame;
import vdw.maxim.bordervisualizer.generateview.Generate_3D_CuboidWand;
import vdw.maxim.bordervisualizer.generateview.ViewObjects;
import vdw.maxim.bordervisualizer.generateview.ViewTypes;
import vdw.maxim.bordervisualizer.locale.Messages;
import vdw.maxim.bordervisualizer.userinterface.SendConsole;
import vdw.maxim.bordervisualizer.userinterface.SendGame;

public class Visualize {

	// Load the arguments
	public static void createVisualize(BorderVisualizer plugin, Player player,
			String viewName, int viewType, String displayName) {
		// Load data from datastore
		LoadData data = new LoadData(plugin);
		int viewObject = data.getViewObject(player);

		if (Config.debugMode == true) {
			SendConsole.info("Starting the Visualize creator...");
		}

		// Draw the shape based on the gathered data
		if (viewObject == ViewObjects._2D_SQUARE) {
			// Load data
			int xpos = data.get2D_SQUARE_Xpos(player);
			int zpos = data.get2D_SQUARE_Zpos(player);
			int size = data.get2D_SQUARE_size(player);
			boolean[] ignore = data.get2D_SQUARE_Ignore(player);

			// Config data
			Material block = Material.getMaterial(Config.blockID);
			int ystart = 0;
			int height = (int) (player.getLocation().getY() + Config.blockHeight);

			// Generate the view
			if (viewType == ViewTypes.VIEW_GLASS_WALL) {
				// Generate the 2D Walls
				Generate_2D_Square generator = new Generate_2D_Square(plugin);
				generator.generate(player, xpos, ystart, zpos, size, height,
						block, ignore);
			} else if (viewType == ViewTypes.VIEW_GLASS_FRAME) {
				// Generate the 2D Walls
				Generate_2D_SquareFrame generator = new Generate_2D_SquareFrame(
						plugin);
				generator.generate(player, xpos, ystart, zpos, size, height,
						block, ignore);
			} else if (viewType == ViewTypes.VIEW_GLASS_WAND) {
				// Generate the 2D Walls
				Generate_2D_SquareWand generator = new Generate_2D_SquareWand(
						plugin);
				generator.generate(player, xpos, ystart, zpos, size, height,
						block, ignore);
			}
		} else if (viewObject == ViewObjects._2D_SQUARESET) {
			// Load data
			int[] xpos = data.get2D_SQUARESET_Xpos(player);
			int[] zpos = data.get2D_SQUARESET_Zpos(player);
			int[] size = data.get2D_SQUARESET_size(player);
			ArrayList<boolean[]> ignore = data.get2D_SQUARESET_Ignore(player);

			for (int i = 0; i < xpos.length; i++) {
				/* DEBUG LOGGING */
				if (Config.debugMode == true) {
					SendConsole.info("Loaded information for 2D_SQUARESET");
					SendConsole.info("Data X: " + xpos[i]);
					SendConsole.info("Data Z: " + zpos[i]);
				}

				// Config data
				Material block = Material.getMaterial(Config.blockID);
				int ystart = 0;
				int height = (int) (player.getLocation().getY() + Config.blockHeight);

				// Generate the view
				if (viewType == ViewTypes.VIEW_GLASS_WALL) {
					// Generate the 2D Walls
					Generate_2D_Square generator = new Generate_2D_Square(
							plugin);
					generator.generate(player, xpos[i], ystart, zpos[i],
							size[i], height, block, ignore.get(i));
				} else if (viewType == ViewTypes.VIEW_GLASS_FRAME) {
					// Generate the 2D Walls
					Generate_2D_SquareFrame generator = new Generate_2D_SquareFrame(
							plugin);
					generator.generate(player, xpos[i], ystart, zpos[i],
							size[i], height, block, ignore.get(i));
				} else if (viewType == ViewTypes.VIEW_GLASS_WAND) {
					// Generate the 2D Walls
					Generate_2D_SquareWand generator = new Generate_2D_SquareWand(
							plugin);
					generator.generate(player, xpos[i], ystart, zpos[i],
							size[i], height, block, ignore.get(i));
				}
			}
		} else if (viewObject == ViewObjects._3D_CUBOID) {
			// Load data
			int xMin = data.get3D_CUBOID_xmin(player);
			int yMin = data.get3D_CUBOID_ymin(player);
			int zMin = data.get3D_CUBOID_zmin(player);
			int xMax = data.get3D_CUBOID_xmax(player);
			int yMax = data.get3D_CUBOID_ymax(player);
			int zMax = data.get3D_CUBOID_zmax(player);

			// Config data
			Material block = Material.getMaterial(Config.blockID);

			// Generate the view
			if (viewType == ViewTypes.VIEW_GLASS_WALL) {
				// Generate the 3D Cuboid
				Generate_3D_Cuboid generator = new Generate_3D_Cuboid(plugin);
				generator.generate(player, xMin, yMin, zMin,
						Math.abs(xMax - xMin), Math.abs(yMax - yMin),
						Math.abs(zMax - zMin), block, null);
			} else if (viewType == ViewTypes.VIEW_GLASS_FRAME) {
				// Generate the 3D Cuboid
				Generate_3D_CuboidFrame generator = new Generate_3D_CuboidFrame(
						plugin);
				generator.generate(player, xMin, yMin, zMin,
						Math.abs(xMax - xMin), Math.abs(yMax - yMin),
						Math.abs(zMax - zMin), block, null);
			} else if (viewType == ViewTypes.VIEW_GLASS_WAND) {
				// Generate the 3D Cuboid
				Generate_3D_CuboidWand generator = new Generate_3D_CuboidWand(
						plugin);
				generator.generate(player, xMin, yMin, zMin,
						Math.abs(xMax - xMin), Math.abs(yMax - yMin),
						Math.abs(zMax - zMin), block, null);
			}
		} else if (viewObject == ViewObjects._2D_RECTANGLE) {
			// Load data
			int xMin = data.get2D_RECTANGLE_xmin(player);
			int zMin = data.get2D_RECTANGLE_zmin(player);
			int xMax = data.get2D_RECTANGLE_xmax(player);
			int zMax = data.get2D_RECTANGLE_zmax(player);

			// Config data
			Material block = Material.getMaterial(Config.blockID);
			int ystart = 0;
			int height = (int) (player.getLocation().getY() + Config.blockHeight);

			// Generate the view
			if (viewType == ViewTypes.VIEW_GLASS_WALL) {
				// Generate the 2D Rectangle
				Generate_2D_Rectangle generator = new Generate_2D_Rectangle(
						plugin);
				generator.generate(player, xMin, ystart, zMin,
						Math.abs(xMax - xMin), height, Math.abs(zMax - zMin),
						block, null);
			} else if (viewType == ViewTypes.VIEW_GLASS_FRAME) {
				// Generate the 2D Rectangle Frame
				Generate_2D_RectangleFrame generator = new Generate_2D_RectangleFrame(
						plugin);
				generator.generate(player, xMin, ystart, zMin,
						Math.abs(xMax - xMin), height, Math.abs(zMax - zMin),
						block, null);
			}
		}
		
		if (viewType != ViewTypes.VIEW_GLASS_WAND) {
			// Send Message
			SendGame.sendMessage(
					Messages.confirm_visualized.replace("{VIEW}", viewName),
					player);
		}
	}

	public static void deleteVisualize(BorderVisualizer plugin, Player player) {
		// Load data from datastore
		LoadData data = new LoadData(plugin);
		int viewObject = data.getViewObject(player);
		int viewType = data.getViewType(player);

		// Draw the shape based on the gathered data
		if (viewObject == ViewObjects._2D_SQUARE) {
			// Load data
			int xpos = data.get2D_SQUARE_Xpos(player);
			int zpos = data.get2D_SQUARE_Zpos(player);
			int size = data.get2D_SQUARE_size(player);
			boolean[] ignore = data.get2D_SQUARE_Ignore(player);

			// Config data
			Material block = Material.AIR;
			int ystart = 0;
			int height = (int) (data.getLocation(player).getY() + Config.blockHeight);

			// Generate the view
			if (viewType == ViewTypes.VIEW_GLASS_WALL) {
				// Generate the 2D Walls
				Generate_2D_Square generator = new Generate_2D_Square(plugin);
				generator.generate(player, xpos, ystart, zpos, size, height,
						block, ignore);
			} else if (viewType == ViewTypes.VIEW_GLASS_FRAME) {
				// Generate the 2D Walls
				Generate_2D_SquareFrame generator = new Generate_2D_SquareFrame(
						plugin);
				generator.generate(player, xpos, ystart, zpos, size, height,
						block, ignore);
			} else if (viewType == ViewTypes.VIEW_GLASS_WAND) {
				// Generate the 2D Walls
				Generate_2D_SquareWand generator = new Generate_2D_SquareWand(
						plugin);
				generator.generate(player, xpos, ystart, zpos,
						size, height, block, ignore);
			}
		} else if (viewObject == ViewObjects._2D_SQUARESET) {
			// Load data
			int[] xpos = data.get2D_SQUARESET_Xpos(player);
			int[] zpos = data.get2D_SQUARESET_Zpos(player);
			int[] size = data.get2D_SQUARESET_size(player);
			ArrayList<boolean[]> ignore = data.get2D_SQUARESET_Ignore(player);

			for (int i = 0; i < xpos.length; i++) {
				/* DEBUG LOGGING */
				if (Config.debugMode == true) {
					SendConsole.info("Loaded information for 2D_SQUARESET");
					SendConsole.info("Data X: " + xpos[i]);
					SendConsole.info("Data Z: " + zpos[i]);
				}

				// Config data
				Material block = Material.AIR;
				int ystart = 0;
				int height = (int) (data.getLocation(player).getY() + Config.blockHeight);

				// Generate the view
				if (viewType == ViewTypes.VIEW_GLASS_WALL) {
					// Generate the 2D Walls
					Generate_2D_Square generator = new Generate_2D_Square(
							plugin);
					generator.generate(player, xpos[i], ystart, zpos[i],
							size[i], height, block, ignore.get(i));
				} else if (viewType == ViewTypes.VIEW_GLASS_FRAME) {
					// Generate the 2D Walls
					Generate_2D_SquareFrame generator = new Generate_2D_SquareFrame(
							plugin);
					generator.generate(player, xpos[i], ystart, zpos[i],
							size[i], height, block, ignore.get(i));
				} else if (viewType == ViewTypes.VIEW_GLASS_WAND) {
					// Generate the 2D Walls
					Generate_2D_SquareWand generator = new Generate_2D_SquareWand(
							plugin);
					generator.generate(player, xpos[i], ystart, zpos[i],
							size[i], height, block, ignore.get(i));
				}
			}
		} else if (viewObject == ViewObjects._3D_CUBOID) {
			// Load data
			int xMin = data.get3D_CUBOID_xmin(player);
			int yMin = data.get3D_CUBOID_ymin(player);
			int zMin = data.get3D_CUBOID_zmin(player);
			int xMax = data.get3D_CUBOID_xmax(player);
			int yMax = data.get3D_CUBOID_ymax(player);
			int zMax = data.get3D_CUBOID_zmax(player);

			// Config data
			Material block = Material.AIR;

			// Generate the view
			if (viewType == ViewTypes.VIEW_GLASS_WALL) {
				// Generate the 3D Cuboid
				Generate_3D_Cuboid generator = new Generate_3D_Cuboid(plugin);
				generator.generate(player, xMin, yMin, zMin,
						Math.abs(xMax - xMin), Math.abs(yMax - yMin),
						Math.abs(zMax - zMin), block, null);
			} else if (viewType == ViewTypes.VIEW_GLASS_FRAME) {
				// Generate the 3D Cuboid
				Generate_3D_CuboidFrame generator = new Generate_3D_CuboidFrame(
						plugin);
				generator.generate(player, xMin, yMin, zMin,
						Math.abs(xMax - xMin), Math.abs(yMax - yMin),
						Math.abs(zMax - zMin), block, null);
			} else if (viewType == ViewTypes.VIEW_GLASS_WAND) {
				// Generate the 3D Cuboid
				Generate_3D_CuboidWand generator = new Generate_3D_CuboidWand(
						plugin);
				generator.generate(player, xMin, yMin, zMin,
						Math.abs(xMax - xMin), Math.abs(yMax - yMin),
						Math.abs(zMax - zMin), block, null);
			}
		} else if (viewObject == ViewObjects._2D_RECTANGLE) {
			// Load data
			int xMin = data.get2D_RECTANGLE_xmin(player);
			int zMin = data.get2D_RECTANGLE_zmin(player);
			int xMax = data.get2D_RECTANGLE_xmax(player);
			int zMax = data.get2D_RECTANGLE_zmax(player);

			// Config data
			Material block = Material.AIR;
			int ystart = 0;
			int height = (int) (data.getLocation(player).getY() + Config.blockHeight);

			// Generate the view
			if (viewType == ViewTypes.VIEW_GLASS_WALL) {
				// Generate the 2D Rectangle
				Generate_2D_Rectangle generator = new Generate_2D_Rectangle(
						plugin);
				generator.generate(player, xMin, ystart, zMin,
						Math.abs(xMax - xMin), height, Math.abs(zMax - zMin),
						block, null);
			} else if (viewType == ViewTypes.VIEW_GLASS_FRAME) {
				// Generate the 2D Rectangle Frame
				Generate_2D_RectangleFrame generator = new Generate_2D_RectangleFrame(
						plugin);
				generator.generate(player, xMin, ystart, zMin,
						Math.abs(xMax - xMin), height, Math.abs(zMax - zMin),
						block, null);
			}
		}

		// Delete all data
		ResetData reset = new ResetData(plugin);
		reset.resetAll(player);
	}
}
