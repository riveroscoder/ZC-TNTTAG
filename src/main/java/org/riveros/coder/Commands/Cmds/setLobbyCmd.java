package org.riveros.coder.Commands.Cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.FileConfig.Messages;
import org.riveros.coder.Utils.AbstractTagSetupCommands;
import org.riveros.coder.Utils.Message;
import org.riveros.coder.Utils.Permissions;

public class setLobbyCmd extends AbstractTagSetupCommands {

	public setLobbyCmd(TNTTag plugin) {
		super(plugin, "setlobby", Messages.getMessage(Message.setLobby), null, new Permissions().setLobby, true);
	}

	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		getPlugin().getDataManager().getData(player).setLobbyLocation();
	}
}
