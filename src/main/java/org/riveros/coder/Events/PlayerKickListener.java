package org.riveros.coder.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import org.riveros.coder.Main.TNTTag;

public class PlayerKickListener implements Listener {
	
	private TNTTag plugin;

	public PlayerKickListener(TNTTag plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void Kick(PlayerKickEvent event) {
		if (plugin.getArenaManager().isInGame(event.getPlayer())) {
			plugin.getArenaManager().removePlayer(event.getPlayer());
		}
	}
}
