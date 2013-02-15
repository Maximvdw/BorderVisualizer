/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 31/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer.DataStore;

import java.util.ArrayList;
import java.util.List;

public class data3DCuboid {
	// Place to save the data
	public static ArrayList<Object> data_xmin = new ArrayList<Object>();
	public static ArrayList<Object> data_xmax = new ArrayList<Object>();
	public static ArrayList<Object> data_ymin = new ArrayList<Object>();
	public static ArrayList<Object> data_ymax = new ArrayList<Object>();
	public static ArrayList<Object> data_zmin = new ArrayList<Object>();
	public static ArrayList<Object> data_zmax = new ArrayList<Object>();
	
	// Add an item/object to the datalist
	public static void addData(int[] min, int[] max)
	{
		// Add the data to the list
		data_xmin.add(min[0]);
		data_ymin.add(min[1]);
		data_zmin.add(min[2]);
		
		data_xmax.add(max[0]);
		data_ymax.add(max[1]);
		data_zmax.add(max[2]);
	}
	
	// Remove an item/object from the datalist
	public static void removeData(int index)
	{
		// Remove the data from the list
		data_xmin.remove(index);
		data_ymin.remove(index);
		data_zmin.remove(index);
		
		data_xmax.remove(index);
		data_ymax.remove(index);
		data_zmax.remove(index);
	}
	
	// Get an object from index
	public static Object getData_minX(int index)
	{
		// Get an item from index
		return data_xmin.get(index);
	}
	
	// Get an object from index
	public static Object getData_minY(int index)
	{
		// Get an item from index
		return data_ymin.get(index);
	}
	
	// Get an object from index
	public static Object getData_minZ(int index)
	{
		// Get an item from index
		return data_zmin.get(index);
	}
	
	// Get an object from index
	public static Object getData_maxX(int index)
	{
		// Get an item from index
		return data_xmax.get(index);
	}
	
	// Get an object from index
	public static Object getData_maxY(int index)
	{
		// Get an item from index
		return data_ymax.get(index);
	}
	
	// Get an object from index
	public static Object getData_maxZ(int index)
	{
		// Get an item from index
		return data_zmax.get(index);
	}
}
