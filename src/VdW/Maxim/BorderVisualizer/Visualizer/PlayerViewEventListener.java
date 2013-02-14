package VdW.Maxim.BorderVisualizer.Visualizer;

import java.util.EventListener;

import VdW.Maxim.BorderVisualizer.BorderVisualizer;
import VdW.Maxim.BorderVisualizer.Events.PlayerViewEvent;

public class PlayerViewEventListener implements EventListener{
	/* Get the plugin information from the main class */
	public BorderVisualizer plugin;

	public PlayerViewEventListener(BorderVisualizer plugin) {
		this.plugin = plugin;
	}
	
	public void onPlayerView(PlayerViewEvent e)
	{
		if (e.getView().equalsIgnoreCase("region"))
		{
			Visualize_WorldGuard_Region region = new Visualize_WorldGuard_Region(null);
			region.visualize_player(e.getPlayer(), 1, e.getDisplay());
		}
	}
}
