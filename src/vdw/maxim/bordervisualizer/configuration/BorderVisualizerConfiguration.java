/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 25/06/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package vdw.maxim.bordervisualizer.configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.configuration.file.FileConfiguration;

import vdw.maxim.bordervisualizer.BorderVisualizer;
import vdw.maxim.bordervisualizer.locale.Messages;
import vdw.maxim.bordervisualizer.userinterface.SendConsole;


public class BorderVisualizerConfiguration {
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	/**
	 * Initialize the BV configuration
	 * @param plugin - BorderVisualizer class
	 */
	public BorderVisualizerConfiguration(BorderVisualizer plugin) {
		this.plugin = plugin;
	}

	// Configuration Files
	public static File configFile = null;
	public static FileConfiguration config = null;

	/**
	 * Check if the configuration exists
	 * @throws Exception - onError
	 */
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

	/**
	 * Update the configuration
	 * @throws Exception - onError
	 */
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

	/**
	 * Copy the configuration files from resources
	 * @param in - InputStream of the resource file
	 * @param file - The file to save it to
	 */
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

	/**
	 * Load the configuration
	 */
	public void loadYamls() {
		try {
			config.load(configFile);
		} catch (Exception e) {
			// Error in configuration
			SendConsole.severe(Messages.error_config_load);
		}
	}

	/**
	 * Save the loaded configuration
	 */
	public void saveYamls() {
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
