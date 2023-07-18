package org.riveros.coder.Commands.Cmds;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.riveros.coder.FileConfig.Messages;
import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.Utils.*;

public class ArenaCmd extends AbstractTagSetupCommands {

	public ArenaCmd(TNTTag plugin) {
		super(plugin, "create", Messages.getMessage(Message.createArena), "<ArenaName>", new Permissions().createArena, true);
	}

	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length == 0) {
			getMessageManager().sendInsuficientArgs(sender, "create", "<ArenaName>");
			return;
		}
		String arenaName = args[0];

		Utils.CCC(player, "");
		Utils.CCC(player, "&b&lAsistente");
		Utils.CCC(player, "&aAsistente de creacion de arenas iniciado con exito!");
		Utils.CCC(player, "");
		player.getInventory().clear();
		player.getInventory().setItem(0, new ItemBuilder(Material.EMPTY_MAP).displayname("&b" + arenaName).lore("&eArena info").build());
		player.getInventory().setItem(2, new ItemBuilder(Material.BEACON).displayname("&a&lSet WaitingSpawn").lore("&eRight Click to interact").build());
		player.getInventory().setItem(3, new ItemBuilder(Material.ENDER_PORTAL).displayname("&d&lSet GameSpawn").lore("&eRight Click to interact").build());
		player.getInventory().setItem(4, new ItemBuilder(Material.EYE_OF_ENDER).displayname("&b&lSet SpectatorSpawn").lore("&eRight Click to interact").build());
		player.getInventory().setItem(8, new ItemBuilder(Material.STAINED_CLAY).displayname("&c&lFinish Setup").lore("&eRight Click to interact").data(new MaterialData(13)).build());
	}
}
