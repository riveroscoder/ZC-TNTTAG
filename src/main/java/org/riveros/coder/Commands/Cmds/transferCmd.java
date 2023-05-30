package org.riveros.coder.Commands.Cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.FileConfig.Messages;
import org.riveros.coder.Utils.AbstractTagCommands;
import org.riveros.coder.Utils.Message;
import org.riveros.coder.Utils.Permissions;

public class transferCmd extends AbstractTagCommands {

	int amount;

	public transferCmd(TNTTag plugin) {
		super(plugin, "transfer", Messages.getMessage(Message.transfer), "coins <player> <amount>", new Permissions().transferCoins, true, "pay");
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (args.length != 2) {
			getMessageManager().sendInsuficientArgs(sender, "transfer", "coins <player> <amount>");
			return;
		}
		
		String playerName = args[1];
		if (getPlayerData().getString(playerName) == null) {
			getMessageManager().sendErrorMessage(sender, playerName + " does not exist!");
			return;
		}
		
		Player player = (Player) sender;
		try {
			this.amount = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			getMessageManager().sendErrorMessage(sender, Messages.getMessage(Message.invalidNumber));
			return;
		}
		
		if (getPlayerData().getInt(player.getName() + ".money") >= this.amount) {
			int giverMoney = getPlayerData().getInt(player.getName() + ".money");
			int recieverMoney = getPlayerData().getInt(playerName + ".money");

			getPlayerData().set(playerName, Integer.valueOf(recieverMoney + this.amount));
			getPlayerData().set(playerName, Integer.valueOf(giverMoney - this.amount));
			sendMessage(sender, ChatColor.GREEN + "Successfully transfered " + this.amount + " to " + playerName + ".");
		}
	}
}
