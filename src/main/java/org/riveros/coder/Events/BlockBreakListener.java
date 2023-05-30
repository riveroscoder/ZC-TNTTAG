package org.riveros.coder.Events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.FileConfig.GameData;
import org.riveros.coder.Utils.Permissions;
import org.riveros.coder.Utils.TNTTagSign;

public class BlockBreakListener implements Listener {

	private TNTTag plugin;

	public BlockBreakListener(TNTTag plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void Break(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (plugin.getArenaManager().isInGame(player)) {
			event.setCancelled(true);
		} else if (plugin.getSignManager().getSignAtLocation(event.getBlock().getLocation()) != null) {
			if (event.getPlayer().hasPermission(new Permissions().deleteSign)) {
				FileConfiguration fc = GameData.getGameData();
				fc.set("signs." + plugin.getSignManager().getSignAtLocation(event.getBlock().getLocation()).getId(), null);
				TNTTagSign.signs.remove(plugin.getSignManager().getSignAtLocation(event.getBlock().getLocation()));
				plugin.getMessageManager().sendMessage(event.getPlayer(), "Cartel de partida eliminado");
			} else {
				event.setCancelled(true);
				plugin.getMessageManager().sendErrorMessage(event.getPlayer(), "Permisos Insuficientes");
			}
		}
	}
}
