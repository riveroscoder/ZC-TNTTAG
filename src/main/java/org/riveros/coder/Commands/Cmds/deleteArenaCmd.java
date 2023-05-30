package org.riveros.coder.Commands.Cmds;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.FileConfig.GameData;
import org.riveros.coder.FileConfig.Messages;
import org.riveros.coder.Utils.AbstractTagSetupCommands;
import org.riveros.coder.Utils.Arena;
import org.riveros.coder.Utils.Message;
import org.riveros.coder.Utils.Permissions;

public class deleteArenaCmd extends AbstractTagSetupCommands {

	public deleteArenaCmd(TNTTag plugin) {
		super(plugin, "deletearena", Messages.getMessage(Message.deleteArena), "<ArenaName|confirm>", new Permissions().deleteArena, true);
	}

	private static HashMap<String, String> strings = new HashMap<String, String>();

	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length == 0) {
			getMessageManager().sendInsuficientArgs(sender, "deletearena", "<ArenaName>");
			StringBuilder arenaNames = new StringBuilder(Messages.getMessage(Message.availableArenas));

			int x = 0;
			for (Arena arena : Arena.arenaObjects) {
				x++;
				arenaNames.append(arena.getName() + (x != Arena.arenaObjects.size() ? ", " : "."));
			}

			getMessageManager().sendMessage(sender, arenaNames.toString());
			return;
		}

		if (args[0].equalsIgnoreCase("confirm")) {
			if (strings.get(player.getName()) != null) {
				FileConfiguration fc = GameData.getGameData();
				fc.set("arenas." + (String) strings.get(player.getName()), null);
				Arena.arenaObjects.remove(getArenaManager().getArena((String) strings.get(player.getName())));
				getMessageManager().sendMessage(sender, Messages.getMessage(Message.arenaDeleted).replace("{arena}", (CharSequence) strings.get(player.getName())));
				strings.remove(player.getName());
				return;
			}
			getMessageManager().sendErrorMessage(sender, Messages.getMessage(Message.unspecifiedArena));
			return;
		}

		String arenaName = args[0];
		if (getArenaManager().getArena(arenaName) != null) {
			getMessageManager().sendMessage(sender, "&cConfirma el comando '/zelitag setup deletearena confirm' para eliminar la Arena");
			strings.put(player.getName(), arenaName);
			addPlayer(player.getName());
		} else {
			getMessageManager().sendErrorMessage(sender, Messages.getMessage(Message.invalidArena));
		}
	}

	private void addPlayer(final String name) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable() {
			public void run() {
				if (deleteArenaCmd.strings.get(name) != null) {
					deleteArenaCmd.strings.remove(name);
				}
			}
		}, 100L);
	}
}
