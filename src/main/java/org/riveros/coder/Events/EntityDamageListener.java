package org.riveros.coder.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import org.riveros.coder.Main.TNTTag;

public class EntityDamageListener implements Listener {

	private TNTTag plugin;

	public EntityDamageListener(TNTTag plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void cancelAllDamage(EntityDamageEvent event) {
		if ((event.getEntity() instanceof Player)) {
			Player player = (Player) event.getEntity();
			if ((event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) && (plugin.getArenaManager().isInGame(player))) {
				event.setCancelled(true);
			}
		}
	}
}
