package org.riveros.coder.Commands.Cmds;

import org.bukkit.command.CommandSender;

import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.FileConfig.Messages;
import org.riveros.coder.Utils.AbstractTagAdminCommands;
import org.riveros.coder.Utils.Message;
import org.riveros.coder.Utils.Permissions;

public class addCmd extends AbstractTagAdminCommands {
	int amount;

	public addCmd(TNTTag plugin) {
		super(plugin, "add", Messages.getMessage(Message.add), "<coins|wins|tags|taggeds> <player>", new Permissions().add, true);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (args.length < 2) {
			getMessageManager().sendInsuficientArgs(sender, "add", "<coins|wins|tags|taggeds> <player>");
			return;
		}

		if (getPlayerData().getString(args[0]) == null) {
			getMessageManager().sendErrorMessage(sender, "Player " + args[0] + " could not be find.");
			return;

		}

		String player = args[0];
		try {
			this.amount = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			getMessageManager().sendErrorMessage(sender, Messages.getMessage(Message.invalidNumber));
			return;
		}
		
		switch (args[1]) {
			case "coins":
				int coins = getPlayerData().getInt(player + ".money");
				getPlayerData().set(player + ".money", Integer.valueOf(coins + this.amount));
				getMessageManager().sendMessage(sender, Messages.getMessage(Message.nowHasCoins).replace("{amount}", getPlayerData().getInt(new StringBuilder(String.valueOf(player)).append(".money").toString()) + "").replace("{player}", player));
				break;
			case "wins":
				int wins = getPlayerData().getInt(player + ".wins");
				getPlayerData().set(player + ".wins", Integer.valueOf(wins + this.amount));
				getMessageManager().sendMessage(sender, Messages.getMessage(Message.nowHasWins).replace("{amount}", getPlayerData().getInt(new StringBuilder(String.valueOf(player)).append(".wins").toString()) + "").replace("{player}", player));
				break;
			case "tags":
				int tags = getPlayerData().getInt(player + ".tags");
				getPlayerData().set(player + ".tags", Integer.valueOf(tags + this.amount));
				getMessageManager().sendMessage(sender, Messages.getMessage(Message.nowHasTags).replace("{amount}", getPlayerData().getInt(new StringBuilder(String.valueOf(player)).append(".tags").toString()) + "").replace("{player}", player));
				break;
			case "taggeds":
				int taggeds = getPlayerData().getInt(player + ".taggeds");
				getPlayerData().set(player + ".taggeds", Integer.valueOf(taggeds + this.amount));
				getMessageManager().sendMessage(sender, Messages.getMessage(Message.nowHasTaggeds).replace("{amount}", getPlayerData().getInt(new StringBuilder(String.valueOf(player)).append(".taggeds").toString()) + "").replace("{player}", player));
				break;
			default:
				getMessageManager().sendInvalidArgs(sender, "add", "<coins|wins|tags|taggeds> <player>");
				break;
		}
	}
}
