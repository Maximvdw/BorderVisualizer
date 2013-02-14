/* -----------------------------
 * Name: BorderVisualizer
 * Version: 1.0.0
 * Last edited: 31/01/2013
 * Author: Maxim Van de Wynckel
 * Nickname: Maximvdw
 * Copyright: 2013
 * ----------------------------- */

package VdW.Maxim.BorderVisualizer.Events;

import java.util.EventObject;

import org.bukkit.entity.Player;

@SuppressWarnings("serial")
public class PlayerViewEvent extends EventObject{
	// Arguments
	public Player player;
	public String display;
	public String view;
	
	public PlayerViewEvent(Object source,String view, Player player, String display) {
		super(source);
		this.player = player;
		this.display = display;
		this.view = view;
	}

	/* Get the visualization to display */
	public String getView()
	{
		return view;
	}
	
	/* Get the player that wants the view */
	public Player getPlayer()
	{
		return player;
	}
	
	/* Get the display argument */
	public String getDisplay()
	{
		return display;
	}
}
