package org.riveros.coder.Commands.Cmds;

import org.bukkit.command.CommandSender;

import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.FileConfig.Messages;
import org.riveros.coder.Utils.AbstractTagAdminCommands;
import org.riveros.coder.Managers.Arena.Arena;
import org.riveros.coder.Utils.Message;
import org.riveros.coder.Utils.Permissions;
import org.riveros.coder.Utils.TNTTagSign;

public class reloadCmd extends AbstractTagAdminCommands {
	
	public reloadCmd(TNTTag plugin ) {
		super(plugin, "reload", Messages.getMessage(Message.reload), null, new Permissions().reload, true);
	}

	public void onCommand(CommandSender sender, String[] args) {
		getFileManager().reloadConfig();
		
		for (Arena arena : Arena.arenaObjects) {
			arena.sendMessage(Messages.getMessage(Message.thereWasReload));
			getArenaManager().endArena(arena);
		}
		
		Arena.arenaObjects.clear();
		TNTTagSign.signs.clear();
		getArenaManager().loadArenas();
		getPlugin().getSignManager().loadSigns();
		getMessageManager().sendMessage(sender, "&aPlugin reiniciado con exito");
	}
}
