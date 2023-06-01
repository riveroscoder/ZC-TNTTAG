package org.riveros.coder.Commands.Cmds;

import org.bukkit.command.CommandSender;

import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.FileConfig.Messages;
import org.riveros.coder.Utils.AbstractTagAdminCommands;
import org.riveros.coder.Managers.Arena.Arena;
import org.riveros.coder.Utils.Message;
import org.riveros.coder.Utils.Permissions;

public class forceStartCmd extends AbstractTagAdminCommands {

	public forceStartCmd(TNTTag plugin) {
		super(plugin, "forcestart", Messages.getMessage(Message.forceStart), null, new Permissions().forceStart, true);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			getMessageManager().sendInsuficientArgs(sender, "forcestart", "<ArenaName>");

			StringBuilder arenaNames = new StringBuilder(Messages.getMessage(Message.availableArenas));

			int x = 0;
			for (Arena arena : Arena.arenaObjects) {
				x++;
				arenaNames.append(arena.getName() + (x != Arena.arenaObjects.size() ? ", " : "."));
			}
			getMessageManager().sendMessage(sender, arenaNames.toString());

			return;
		}
		
		String arenaName = args[0];
		if (getArenaManager().getArena(arenaName) != null) {
			getArenaManager().forceStartArena(arenaName, sender);
			return;
		}
		
		getMessageManager().sendErrorMessage(sender, Messages.getMessage(Message.invalidArena));
		StringBuilder arenaNames = new StringBuilder(Messages.getMessage(Message.availableArenas));

		int x = 0;
		for (Arena arena : Arena.arenaObjects) {
			x++;
			arenaNames.append(arena.getName() + (x != Arena.arenaObjects.size() ? ", " : "."));
		}
		getMessageManager().sendMessage(sender, arenaNames.toString());
	}
}
