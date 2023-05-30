package org.riveros.coder.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import org.riveros.coder.Main.TNTTag;

public class FoodLevelChangeListener implements Listener {
	
	private TNTTag plugin;

	public FoodLevelChangeListener(TNTTag plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		if (plugin.getArenaManager().isInGame((Player) event.getEntity())) {
			event.setCancelled(true);
		}
	}
}
