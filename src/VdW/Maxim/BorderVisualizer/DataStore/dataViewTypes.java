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


public class dataViewTypes {
	// Place to save the data
	public static ArrayList<Object> data = new ArrayList<Object>();
	
	// Add an item/object to the datalist
	public static void addData(Object object)
	{
		// Add the data to the list
		data.add(object);
	}
	
	// Remove an item/object from the datalist
	public static void removeData(int index)
	{
		// Remove the data from the list
		data.remove(index);
	}
	
	// Get an object from index
	public static Object getData(int index)
	{
		// Get an item from index
		return data.get(index);
	}
	
	// Check if an item is included
	public static boolean contains(Object object)
	{
		// Check if data is available
		return data.contains(object);
	}
	
	// Get the index from the object/item
	public static int getIndex(Object object)
	{
		// Get the index from the object
		return data.indexOf(object);
	}
}
