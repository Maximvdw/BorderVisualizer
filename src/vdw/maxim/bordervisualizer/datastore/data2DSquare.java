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

public class data2DSquare {
	// Place to save the data
	public static ArrayList<Object> data_x = new ArrayList<Object>();
	public static ArrayList<Object> data_z = new ArrayList<Object>();
	public static ArrayList<Object> data_size = new ArrayList<Object>();
	public static ArrayList<Object> data_ignore = new ArrayList<Object>();
	
	// Add an item/object to the datalist
	public static void addData(Object x,Object z,Object size, Object ignore)
	{
		// Add the data to the list
		data_x.add(x);
		data_z.add(z);
		data_size.add(size);
		data_ignore.add(ignore);
	}
	
	// Remove an item/object from the datalist
	public static void removeData(int index)
	{
		// Remove the data from the list
		data_x.remove(index);
		data_z.remove(index);
		data_size.remove(index);
	}
	
	// Get an object from index
	public static Object getData_X(int index)
	{
		// Get an item from index
		return data_x.get(index);
	}

	// Get an object from index
	public static Object getData_Ignore(int index)
	{
		// Get an item from index
		return data_ignore.get(index);
	}
	
	// Get an object from index
	public static Object getData_Z(int index)
	{
		// Get an item from index
		return data_z.get(index);
	}
	
	// Get an object from index
	public static Object getData_Size(int index)
	{
		// Get an item from index
		return data_size.get(index);
	}
}
