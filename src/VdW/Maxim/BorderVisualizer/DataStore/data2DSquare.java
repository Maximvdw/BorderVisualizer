/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 31/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer.DataStore;

import java.util.List;

public class data2DSquare {
	// Place to save the data
	public static List<Object> data_x;
	public static List<Object> data_z;
	public static List<Object> data_size;
	
	// Add an item/object to the datalist
	public static void addData(Object x,Object z,Object size)
	{
		// Add the data to the list
		data_x.add(x);
		data_z.add(z);
		data_size.add(size);
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
