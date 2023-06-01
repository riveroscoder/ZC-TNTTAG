package org.riveros.coder.Commands.Cmds;

import org.bukkit.command.CommandSender;

import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.FileConfig.Messages;
import org.riveros.coder.Utils.AbstractTagCommands;
import org.riveros.coder.Managers.Arena.Arena;
import org.riveros.coder.Utils.Message;
import org.riveros.coder.Utils.Permissions;

public class listArenasCmd extends AbstractTagCommands {
	
	public listArenasCmd(TNTTag plugin) {
		super(plugin, "listArenas", Messages.getMessage(Message.listArenas), null, new Permissions().join, true, "arenas");
	}

	public void onCommand(CommandSender sender, String[] args) {
		StringBuilder arenaNames = new StringBuilder(Messages.getMessage(Message.availableArenas));
		int x = 0;
		
		for (Arena arena : Arena.arenaObjects) {
			x++;
			arenaNames.append(arena.getName() + (x != Arena.arenaObjects.size() ? ", " : "."));
		}
		getMessageManager().sendMessage(sender, arenaNames.toString());
	}
}
