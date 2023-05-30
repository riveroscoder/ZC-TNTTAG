package org.riveros.coder.Commands.Cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.FileConfig.Messages;
import org.riveros.coder.Utils.AbstractTagCommands;
import org.riveros.coder.Utils.Message;
import org.riveros.coder.Utils.Permissions;

public class coinsCmd extends AbstractTagCommands {

	public coinsCmd(TNTTag plugin) {
		super(plugin, "coins", Messages.getMessage(Message.coins), "", new Permissions().checkCoins, true, "money");
	}

	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		sendMessage(sender, Messages.getMessage(Message.checkCoins).replace("{amount}", getPlayerData().getString(player.getName())));
	}
}
