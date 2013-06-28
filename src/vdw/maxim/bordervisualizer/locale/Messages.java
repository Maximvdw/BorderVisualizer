/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 31/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package vdw.maxim.bordervisualizer.locale;

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
	public static String error_view_distance = 
			"&cThe view is too far away from your position!";
	/* END ERROR MESSAGES */
	
	
	/* WARNING MESSAGES */
	public static String warning_nextview =
			"&c[!]&e Removed previous {VIEW} border!";
	public static String warning_player_quit = 
			"&e{PLAYER} was removed from list {LIST}!";
	public static String warning_movement = 
			"&c[!]&e {VIEW} border disabled because of movement.";
	public static String warning_commandhook = 
			"&eThe command '{COMMAND}' has already been registered! - {PLUGIN}";
	public static String warning_forcereload = 
			"&c[!]&e BorderVisualizer reloaded suddenly while your border was visible!\n" +
			"&eThe border might still be visible, relog to remove it.";
	/* END WARNING MESSAGES */
	
	
	/* CONFIRM MESSAGES */
	public static String confirm_copy_config = 
			"&aCopying config.yml file!";
	public static String config_visualized = 
			"&2[&fBorderVisualizer&2]&a Visualized {VIEW}.";
	public static String config_removed = 
			"&2[&fBorderVisualizer&2]&e Removed border.";
	/* END CONFIRM MESSAGES */
	
	
	/* HELP MESSAGES */
	public static String help_header = 
			"&6--------[ BorderVisualizer]--------";
	public static String help_worldguard_region =
			"&cPlease define a regionID! &4/bv region <id>";
	/* END HELP MESSAGES */
}