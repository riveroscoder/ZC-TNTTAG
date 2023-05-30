package org.riveros.coder.Commands.Cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.FileConfig.Messages;
import org.riveros.coder.Utils.AbstractTagSetupCommands;
import org.riveros.coder.Utils.Message;
import org.riveros.coder.Utils.Permissions;

public class setSpectatorsCmd extends AbstractTagSetupCommands {
	
	public setSpectatorsCmd(TNTTag plugin) {
		super(plugin, "setspec", Messages.getMessage(Message.setSpectatorsPoint), null, new Permissions().setSpec, true);
	}

	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		getPlugin().getDataManager().getData(player).setSpectatorsLocation();
	}
}
