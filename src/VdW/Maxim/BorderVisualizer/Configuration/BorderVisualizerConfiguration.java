/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 31/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer.Configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.configuration.file.FileConfiguration;

import VdW.Maxim.BorderVisualizer.BorderVisualizer;
import VdW.Maxim.BorderVisualizer.Locale.Messages;
import VdW.Maxim.BorderVisualizer.UserInterface.SendConsole;

public class BorderVisualizerConfiguration {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public BorderVisualizerConfiguration(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	// Configuration Files
	public static File configFile;
	public static FileConfiguration config;

	public void firstRun() throws Exception {
		try {

			if (!configFile.exists()) {
				configFile.getParentFile().mkdirs();
				SendConsole.info(Messages.confirm_copy_config);
				copy(plugin.getResource("config.yml"), configFile);
			}
		} catch (Exception ex) {
			// Critical error
			SendConsole.severe(Messages.error_config_copy);
		}
	}

	public void update() throws Exception {
		try {
			configFile.getParentFile().mkdirs();
			SendConsole.info(Messages.confirm_copy_config);
			copy(plugin.getResource("config.yml"), configFile);
		} catch (Exception ex) {
			// Critical error
			SendConsole.severe(Messages.error_config_copy);
		}
	}

	private void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadYamls() {
		try {
			config.load(configFile);
		} catch (Exception e) {
			// Error in configuration
			SendConsole.severe(Messages.error_config_load);
			SendConsole.severe(e.getMessage());
		}
	}

	public void saveYamls() {
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
