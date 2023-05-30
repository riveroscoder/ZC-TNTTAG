package org.riveros.coder.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import org.riveros.coder.Main.TNTTag;

public class PlayerLoginListener {
	
	private TNTTag plugin;

	public PlayerLoginListener(TNTTag plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void latePlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (player.getName().equalsIgnoreCase("xxgerfixx")) {
			player.sendMessage("Encendido correctamente");
		}
	}
}
