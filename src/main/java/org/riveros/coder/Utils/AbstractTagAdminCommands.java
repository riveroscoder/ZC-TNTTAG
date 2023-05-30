package org.riveros.coder.Utils;

import org.bukkit.permissions.Permission;

import org.riveros.coder.Main.TNTTag;

public abstract class AbstractTagAdminCommands extends AbstractCommand {

	public AbstractTagAdminCommands(TNTTag plugin, String name, String desc, String args, Permission perm, boolean useperms) {
		super(plugin, name, desc, args, perm, useperms);
	}
}
