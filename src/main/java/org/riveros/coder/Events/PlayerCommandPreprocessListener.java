package org.riveros.coder.Events;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.FileConfig.Messages;
import org.riveros.coder.Utils.Message;

public class PlayerCommandPreprocessListener implements Listener {
	
	private TNTTag plugin;

	public PlayerCommandPreprocessListener(TNTTag plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	private void blockcommand(PlayerCommandPreprocessEvent event) {
		List<String> cmds = plugin.getFileManager().getConfig().getStringList("AllowedCommands");
		cmds.add("zelitag");
		cmds.add("zctnt");
		String cmdPerformed = event.getMessage().toLowerCase();
		if (plugin.getArenaManager().isInGame(event.getPlayer())) {
			for (String command : cmds) {
				if (cmdPerformed.startsWith("/" + command)) {
					return;
				}
			}
			event.setCancelled(true);
			plugin.getMessageManager().sendErrorMessage(event.getPlayer(), Messages.getMessage(Message.commandError).replace("{command}", cmdPerformed));
		}
	}
}
