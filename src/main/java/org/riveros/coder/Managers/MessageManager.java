package org.riveros.coder.Managers;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.riveros.coder.FileConfig.Messages;
import org.riveros.coder.Utils.Arena;
import org.riveros.coder.Utils.Message;

public class MessageManager {
	
	private String prefix;

	public MessageManager() {
		prefix = ChatColor.translateAlternateColorCodes('&', ("&5&LTNT-TAG "));
	}
	
	public void sendErrorMessage(CommandSender sender, String errormsg) {
		sender.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " " + errormsg);
	}

	public void sendInsuficientArgs(CommandSender sender, String command, String args) {
		sendErrorMessage(sender, "Insufficient args.");}

	public void sendInvalidArgs(CommandSender sender, String command, String args) {
		sendErrorMessage(sender, "Invalid args.");
	}

	public void sendMessage(CommandSender sender, String s) {
		sender.sendMessage(this.prefix + ChatColor.translateAlternateColorCodes('&', (s)));
	}

	public void sendInGamePlayersMessage(String s, Arena arena) {
		arena.sendMessage(s);
	}

	public void sendInGamePlayersTitle(String s, String s2, Arena arena) {
		arena.sendTitle(ChatColor.translateAlternateColorCodes('&', (s)), ChatColor.translateAlternateColorCodes('&', (s2)));
	}

	public void sendWinMessage(Player player2, String s, Arena arena) {
		for (Player player : arena.getPlayers()) {
			player.sendMessage("");
			player.sendMessage(Messages.getMessage(Message.winMessage).replace("{player}", s));
			player.sendMessage("");
		}
		player2.sendMessage("");
		player2.sendMessage(Messages.getMessage(Message.winMessage).replace("{player}", s));
		player2.sendMessage("");

	}

	public void isConsole(CommandSender sender) {
		sender.sendMessage(this.prefix + ChatColor.RED + "This command can only be used by an in-game player!");
	}

	public void noperm(CommandSender sender) {
		sender.sendMessage(this.prefix + ChatColor.GRAY + "You do not have permission to perform this command!");
	}

	public void sendNoPrefixMessage(CommandSender sender, String s) {
		sender.sendMessage(ChatColor.GRAY + s);
	}
}
