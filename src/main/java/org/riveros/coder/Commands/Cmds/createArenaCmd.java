package org.riveros.coder.Commands.Cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.FileConfig.Messages;
import org.riveros.coder.Utils.AbstractTagSetupCommands;
import org.riveros.coder.Utils.Message;
import org.riveros.coder.Utils.Permissions;

public class createArenaCmd extends AbstractTagSetupCommands {
	
	public createArenaCmd(TNTTag plugin) {
		super(plugin, "createarena", Messages.getMessage(Message.createArena), "<ArenaName>", new Permissions().createArena, true);
	}

	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length == 0) {
			getMessageManager().sendInsuficientArgs(sender, "createarena", "<ArenaName>");
			return;
		}
		String arenaName = args[0];
		if (!getPlugin().getDataManager().getData(player).check()) {
			getPlugin().getDataManager().getData(player).createArena(arenaName);
			getMessageManager().sendMessage(sender, Messages.getMessage(Message.arenaCreated).replace("{arena}", arenaName));
		}
	}
}
