package org.riveros.coder.Commands.Cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.FileConfig.Messages;
import org.riveros.coder.Utils.AbstractTagCommands;
import org.riveros.coder.Utils.Arena;
import org.riveros.coder.Utils.Message;
import org.riveros.coder.Utils.Permissions;

public class joinCmd extends AbstractTagCommands {

	public joinCmd(TNTTag plugin) {
		super(plugin, "join", Messages.getMessage(Message.join), "<ArenaName>", new Permissions().join, true, "j");
	}

	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length == 0) {
			getMessageManager().sendInsuficientArgs(sender, "join", "<ArenaName>");
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
		if (!getArenaManager().isInGame(player)) {
			getArenaManager().addPlayers(player, arenaName);
			return;
		}

		getMessageManager().sendErrorMessage(sender, Messages.getMessage(Message.leaveCurrentArena));
	}
}
