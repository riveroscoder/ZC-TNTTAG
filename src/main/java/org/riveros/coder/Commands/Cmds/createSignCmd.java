package org.riveros.coder.Commands.Cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.FileConfig.Messages;
import org.riveros.coder.Utils.AbstractTagSetupCommands;
import org.riveros.coder.Utils.Arena;
import org.riveros.coder.Utils.Message;
import org.riveros.coder.Utils.Permissions;

public class createSignCmd extends AbstractTagSetupCommands {

	public createSignCmd(TNTTag plugin) {
		super(plugin, "createsign", ChatColor.translateAlternateColorCodes('&',("&aCrear cartel")), "<arena>", new Permissions().createSign, true);
	}

	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length == 0) {
			getMessageManager().sendInsuficientArgs(sender, "createsign", "<arena>");
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
		if (getArenaManager().getArena(arenaName) == null) {
			getMessageManager().sendErrorMessage(sender, Messages.getMessage(Message.arenaNotFound));
			StringBuilder arenaNames = new StringBuilder(Messages.getMessage(Message.availableArenas));

			int x = 0;
			for (Arena arena : Arena.arenaObjects) {
				x++;
				arenaNames.append(arena.getName() + (x != Arena.arenaObjects.size() ? ", " : "."));
			}

			getMessageManager().sendMessage(sender, arenaNames.toString());
			return;
		}

		getPlugin().getSignManager().tempSign.put(player.getName(), arenaName);
		getMessageManager().sendMessage(sender, "&cDale click a un cartel para asignar a la arena: " + arenaName);
		addToTempList(player);
	}

	private void addToTempList(final Player player) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable() {
			public void run() {
				if (getPlugin().getSignManager().tempSign.containsKey(player.getName())) {
					getPlugin().getSignManager().tempSign.remove(player.getName());
				}
			}
		}, 1200L);
	}
}
