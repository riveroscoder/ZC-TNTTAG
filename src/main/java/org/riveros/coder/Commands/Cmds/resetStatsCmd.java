package org.riveros.coder.Commands.Cmds;

import org.bukkit.command.CommandSender;

import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.Utils.AbstractTagAdminCommands;
import org.riveros.coder.Utils.Permissions;

public class resetStatsCmd extends AbstractTagAdminCommands {
	
	public resetStatsCmd(TNTTag plugin) {
		super(plugin, "resetstats", "Reset stats for a player.", "<player>", new Permissions().resetStats, true);
	}

	public void onCommand(CommandSender sender, String[] args) {
	}
}
