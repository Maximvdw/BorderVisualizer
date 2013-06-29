/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 31/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package vdw.maxim.bordervisualizer.configuration;

public class Config {
	// Configuration Settings
	public static boolean enabled = true;
	public static boolean allowConsoleColor = true;
	public static boolean allowOpPermission = true;
	public static boolean allowPlayerMoveEvent = true;
	public static boolean allowAllCommands = false;
	public static boolean allowAuthorPermissions = true;
	public static boolean debugMode = true;
	public static boolean cornersOnly = false;
	public static int blockID = 20;
	public static int blockHeight = 20;
	public static int squareView = 0;
	public static int cuboidView = 1;
	public static int rectangleView = 0;

	public static void initConfig() {
		try {
			Config.enabled = BorderVisualizerConfiguration.config
					.getBoolean("enabled");
		} catch (Exception ex) {
			// Could not load that setting
		}
		try {
			Config.allowOpPermission = BorderVisualizerConfiguration.config
					.getBoolean("allowOpPermissions");
		} catch (Exception ex) {
			// Could not load that setting
		}
		try {
			Config.allowConsoleColor = BorderVisualizerConfiguration.config
					.getBoolean("allowConsoleColor");
		} catch (Exception ex) {
			// Could not load that setting
		}
		try {
			Config.allowPlayerMoveEvent = BorderVisualizerConfiguration.config
					.getBoolean("allowPlayerMoveEvent");
		} catch (Exception ex) {
			// Could not load that setting
		}
		try {
			Config.allowAuthorPermissions = BorderVisualizerConfiguration.config
					.getBoolean("allowAuthorPermissions");
		} catch (Exception ex) {
			// Could not load that setting
		}
		try {
			Config.allowAllCommands = BorderVisualizerConfiguration.config
					.getBoolean("allowAllCommands");
		} catch (Exception ex) {
			// Could not load that setting
		}
		try {
			Config.debugMode = BorderVisualizerConfiguration.config
					.getBoolean("debugMode");
		} catch (Exception ex) {
			// Could not load that setting
		}
		try {
			Config.blockID = BorderVisualizerConfiguration.config
					.getInt("blockID");
		} catch (Exception ex) {
			// Could not load that setting
		}
		try {
			Config.blockHeight = BorderVisualizerConfiguration.config
					.getInt("height");
		} catch (Exception ex) {
			// Could not load that setting
		}
		try {
			String viewType = BorderVisualizerConfiguration.config
					.getString("cuboid");
			if (viewType.equalsIgnoreCase("wall")){
				cuboidView = 0;
			}else if (viewType.equalsIgnoreCase("frame")){
				cuboidView = 1;
			}
		} catch (Exception ex) {
			// Could not load that setting
		}
		try {
			String viewType = BorderVisualizerConfiguration.config
					.getString("rectangle");
			if (viewType.equalsIgnoreCase("wall")){
				rectangleView = 0;
			}else if (viewType.equalsIgnoreCase("frame")){
				rectangleView = 1;
			}
		} catch (Exception ex) {
			// Could not load that setting
		}
		try {
			String viewType = BorderVisualizerConfiguration.config
					.getString("square");
			if (viewType.equalsIgnoreCase("wall")){
				squareView = 0;
			}else if (viewType.equalsIgnoreCase("frame")){
				squareView = 1;
			}
		} catch (Exception ex) {
			// Could not load that setting
		}
	}
}
