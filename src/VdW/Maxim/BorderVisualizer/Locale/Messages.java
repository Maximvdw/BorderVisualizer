/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 31/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer.Locale;

public class Messages {
	/* ERROR MESSAGES */
	public static String error_nopermission = 
			"&cYou do not have Permission!";
	public static String error_noconsole = 
			"&cThis can only be used ingame!";
	public static String error_wg_notsupported =
			"&cThe shape of the region is not supported (yet)!";
	public static String error_wg_notfound = 
			"&cThe region '{REGION}' was not found!";
	public static String error_towny_town_notfound = 
			"&cThe town '{TOWN}' was not found!";
	public static String error_nolocation = 
			"&cThere is no {VIEW} at your location!";
	public static String error_config_copy =
			"&cUnable to copy the config files!";
	public static String error_config_load = 
			"&cAn error ocured while loading the config!";
	public static String error_plugin_notfound = 
			"&2[&fBorderVisualizer&2]&c The plugin {PLUGIN} was not found!";
	/* END ERROR MESSAGES */
	
	
	/* WARNING MESSAGES */
	public static String warning_nextview =
			"&c[!]&e Removed previous {VIEW} visualization!";
	public static String warning_player_quit = 
			"&e{PLAYER} was removed from list {LIST}!";
	public static String warning_movement = 
			"&c[!]&e {VIEW} visualization disabled because of movement.";
	/* END WARNING MESSAGES */
	
	
	/* CONFIRM MESSAGES */
	public static String confirm_copy_config = 
			"&aCopying config.yml file!";
	public static String config_visualized = 
			"&2[&fBorderVisualizer&2]&a Visualized {VIEW}.";
	/* END CONFIRM MESSAGES */
	
	
	/* HELP MESSAGES */
	public static String help_header = 
			"&6--------[ BorderVisualizer]--------";
	public static String help_worldguard_region =
			"&cPlease define a regionID! &4/bv region <id>";
	/* END HELP MESSAGES */
}
