package org.riveros.coder.Commands.Cmds;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.riveros.coder.FileConfig.Messages;
import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.Utils.*;

public class setspawnCmd extends AbstractTagAdminCommands {

	public setspawnCmd(TNTTag plugin ) {
		super(plugin, "setspawn", "Success", null, new Permissions().setspawn, true);
	}

	public void onCommand(CommandSender sender, String[] args) {
		getFileManager().reloadConfig();

		Player p = (Player) sender;
		Location location = p.getLocation();
		getFileManager().getConfig().set("Lobby.SpawnLoc", location);
	}
}
