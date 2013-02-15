/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 31/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer.RegisterView;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import VdW.Maxim.BorderVisualizer.BorderVisualizer;
import VdW.Maxim.BorderVisualizer.Locale.Messages;
import VdW.Maxim.BorderVisualizer.UserInterface.SendConsole;

public class RegisterView {
	public static boolean add(String viewName, String viewCommand, String pluginName)
	{
		// First check all arguments
		if ((viewName != "") && (viewCommand != "") && (pluginName != ""))
		{
			// All arguments are valid
			// Check if plugin exists
			
			// Define the plugin Manager
			PluginManager pm = Bukkit.getServer().getPluginManager();
			try {
				Plugin p = pm.getPlugin(pluginName);
				if (p != null) {			

				}else{
					return false; // Error plugin is not valid
				}
				
				// Now check if the command is already given
				if (BorderVisualizer.bv_view_command.contains(viewCommand))
				{
					// Display message
					SendConsole.warning(Messages.warning_commandhook.
							replace("{COMMAND}", viewCommand).replace("{PLUGIN}", pluginName));
					return false;
				}else{
					// Add data
					BorderVisualizer.bv_view_command.add(viewCommand); // Unique
					BorderVisualizer.bv_view_name.add(viewName);
					BorderVisualizer.bv_view_plugin.add(pluginName);
					return true;
				}
			} catch (Exception ex) {
				return false; // Error
			}
		}else{
			// False arguments incorrect
			return false;
		}
	}
}
