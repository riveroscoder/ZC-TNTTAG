package org.riveros.coder.Commands.Cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.FileConfig.Messages;
import org.riveros.coder.Utils.AbstractTagCommands;
import org.riveros.coder.Utils.Message;
import org.riveros.coder.Utils.Permissions;

public class leaveCmd extends AbstractTagCommands {

	public leaveCmd(TNTTag plugin) {
		super(plugin, "leave", Messages.getMessage(Message.leave), null, new Permissions().leave, true, "l");
	}

	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (getArenaManager().isInGame(player)) {
			getArenaManager().removePlayer(player);
			return;
		}

		getMessageManager().sendErrorMessage(sender, Messages.getMessage(Message.notInArena));
	}
}
