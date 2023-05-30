package org.riveros.coder.Utils;

import org.bukkit.permissions.Permission;

import org.riveros.coder.Main.TNTTag;

public abstract class AbstractTagSetupCommands extends AbstractCommand {

	public AbstractTagSetupCommands(TNTTag plugin, String name, String desc, String args, Permission perm, boolean useperms) {
		super(plugin, name, desc, args, perm, useperms);
	}

}
