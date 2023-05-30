package org.riveros.coder.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import org.riveros.coder.Main.TNTTag;

public class InventoryClickListener implements Listener {
	
	private TNTTag plugin;

	public InventoryClickListener(TNTTag plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void InventoryMove(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		if (plugin.getArenaManager().isInGame(player)) {
			e.setCancelled(true);
		}
	}
}
