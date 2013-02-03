/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 31/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer.GenerateView;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import VdW.Maxim.BorderVisualizer.BorderVisualizer;

public class Generate_3D_Cuboid {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public Generate_3D_Cuboid(BorderVisualizer plugin) {
		this.plugin = plugin;
	}
	
	public void generate(Player player, int x, int z, int size,
			int height, Material block, boolean[] ignore) {
		
	}
}
