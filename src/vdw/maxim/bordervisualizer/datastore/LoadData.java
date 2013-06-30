/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 31/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package vdw.maxim.bordervisualizer.datastore;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import vdw.maxim.bordervisualizer.BorderVisualizer;
import vdw.maxim.bordervisualizer.generateview.View2DSquare;


public class LoadData {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public LoadData(BorderVisualizer plugin) {
		this.plugin = plugin;
	}
	
	// Get glassigied
	public Location[] getGlassified(Player player)
	{
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			return (Location[])dataGlassified.getData(index);
		}else{ return null; }
	}
	
	// Get if there is a nomove
	public boolean getAllowMove(Player player)
	{
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			return (boolean)dataAllowMove.getData(index);
		}else{ return true; }
	}
	
	// Get the ViewObject from the dataStore
	public int getViewObject(Player player){
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			return (int)dataViewObjects.getData(index);
		}else{ return -1; }
	}
	
	// Get the ViewType from the dataStore
	public int getViewType(Player player){
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			return (int)dataViewTypes.getData(index);
		}else{ return -1; }
	}
	
	// Get the view name from the dataStore
	public String getViewName(Player player){
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			return (String) dataViews.getData(index);
		}else{ return null; }
	}
	
	// Get the location of the player before executing command
	public Location getLocation(Player player){
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			return (Location) dataLocation.getData(index);
		}else{ return null; }
	}
	
	
	// Load the ignore faces for 2D square
	public boolean[] get2D_SQUARE_Ignore(Player player)
	{
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			return (boolean[])data2DSquare.getData_Ignore(index);
		}else{ return null; }
	}
	
	// Load X pos for 2D SQUARE
	public int get2D_SQUARE_Xpos(Player player)
	{
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			return (int)data2DSquare.getData_X(index);
		}else{ return -1; }
	}
	
	// Load Z pos for 2D SQUARE
	public int get2D_SQUARE_Zpos(Player player)
	{
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			return (int)data2DSquare.getData_Z(index);
		}else{ return -1; }
	}
	
	// Load size for 2D SQUARE
	public int get2D_SQUARE_size(Player player)
	{
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			return (int)data2DSquare.getData_Size(index);
		}else{ return -1; }
	}
	
	// Load the ignore faces for 2D SQUARESET
	public ArrayList<boolean[]> get2D_SQUARESET_Ignore(Player player)
	{
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			View2DSquare[] squareBlocks = (View2DSquare[]) data2DSquareSet.getData(index);
			ArrayList<boolean[]> data = new ArrayList<boolean[]>();
			for (int i = 0;i<squareBlocks.length;i++)
			{
				data.add((boolean[]) squareBlocks[i].getData_Ignore(index));
			}
			// Return result
			return data;
		}else{ return null; }
	}
	
	// Load X pos for 2D SQUARESET
	public int[] get2D_SQUARESET_Xpos(Player player)
	{
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			View2DSquare[] squareBlocks = (View2DSquare[]) data2DSquareSet.getData(index);
			int[] data = new int[squareBlocks.length];
			for (int i = 0;i<data.length;i++)
			{
				data[i] = (int) squareBlocks[i].getData_X(index);
			}
			
			// Return result
			return data;
		}else{ return null; }
	}
	
	// Load Z pos for 2D SQUARESET
	public int[] get2D_SQUARESET_Zpos(Player player)
	{
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			View2DSquare[] squareBlocks = (View2DSquare[]) data2DSquareSet.getData(index);
			int[] data = new int[squareBlocks.length];
			for (int i = 0;i<data.length;i++)
			{
				data[i] = (int) squareBlocks[i].getData_Z(index);
			}
			// Return result
			return data;
		}else{ return null; }
	}
	
	// Load size for 2D SQUARESET
	public int[] get2D_SQUARESET_size(Player player)
	{
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			View2DSquare[] squareBlocks = (View2DSquare[]) data2DSquareSet.getData(index);
			int[] data = new int[squareBlocks.length];
			for (int i = 0;i<data.length;i++)
			{
				data[i] = (int) squareBlocks[i].getData_Size(index);
			}
			// Return result
			return data;
		}else{ return null; }
	}
	
	// Load xmax for 3D CUBOID
	public int get3D_CUBOID_xmax(Player player)
	{
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			return (int)data3DCuboid.getData_maxX(index);
		}else{ return -1; }
	}
	
	// Load ymax for 3D CUBOID
	public int get3D_CUBOID_ymax(Player player)
	{
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			return (int)data3DCuboid.getData_maxY(index);
		}else{ return -1; }
	}
	
	// Load zmax for 3D CUBOID
	public int get3D_CUBOID_zmax(Player player)
	{
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			return (int)data3DCuboid.getData_maxZ(index);
		}else{ return -1; }
	}
	
	// Load xmin for 3D CUBOID
	public int get3D_CUBOID_xmin(Player player)
	{
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			return (int)data3DCuboid.getData_minX(index);
		}else{ return -1; }
	}
	
	// Load ymin for 3D CUBOID
	public int get3D_CUBOID_ymin(Player player)
	{
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			return (int)data3DCuboid.getData_minY(index);
		}else{ return -1; }
	}
	
	// Load zmin for 3D CUBOID
	public int get3D_CUBOID_zmin(Player player)
	{
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			return (int)data3DCuboid.getData_minZ(index);
		}else{ return -1; }
	}
	
	// Load xmax for 3D CUBOID
	public int get2D_RECTANGLE_xmax(Player player)
	{
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			return (int)data2DRectangle.getData_maxX(index);
		}else{ return -1; }
	}
	
	// Load zmax for 3D CUBOID
	public int get2D_RECTANGLE_zmax(Player player)
	{
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			return (int)data2DRectangle.getData_maxZ(index);
		}else{ return -1; }
	}
	
	// Load xmin for 3D CUBOID
	public int get2D_RECTANGLE_xmin(Player player)
	{
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			return (int)data2DRectangle.getData_minX(index);
		}else{ return -1; }
	}
	
	// Load zmin for 3D CUBOID
	public int get2D_RECTANGLE_zmin(Player player)
	{
		if (dataPlayers.contains(player))
		{
			// Now get the index of the item/player
			int index = dataPlayers.getIndex(player);
			return (int)data2DRectangle.getData_minZ(index);
		}else{ return -1; }
	}
}
