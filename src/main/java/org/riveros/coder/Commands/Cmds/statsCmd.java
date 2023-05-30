package org.riveros.coder.Commands.Cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.FileConfig.Messages;
import org.riveros.coder.Utils.AbstractTagCommands;
import org.riveros.coder.Utils.Message;
import org.riveros.coder.Utils.Permissions;

public class statsCmd extends AbstractTagCommands {
	
	public statsCmd(TNTTag plugin) {
		super(plugin, "checkstats", Messages.getMessage(Message.checkStats), null, new Permissions().checkStats, true, "stats");
	}

	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		int money = getPlayerData().getInt(player.getName() + ".money");
		int tags = getPlayerData().getInt(player.getName() + ".tags");
		int taggeds = getPlayerData().getInt(player.getName() + ".taggeds");
		int wins = getPlayerData().getInt(player.getName() + ".wins");

		sendMessage(sender, Messages.getMessage(Message.stats).replace("{money}", money + "").replace("{tags}", tags + "").replace("{taggeds}", taggeds + "").replace("{wins}", wins + ""));
	}
}
