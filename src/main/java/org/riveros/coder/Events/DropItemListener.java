package org.riveros.coder.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import org.riveros.coder.Main.TNTTag;

public class DropItemListener implements Listener {
	
	private TNTTag plugin;

	public DropItemListener(TNTTag plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void Drop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (plugin.getArenaManager().isInGame(player)) {
			event.setCancelled(true);
		}
	}
}
