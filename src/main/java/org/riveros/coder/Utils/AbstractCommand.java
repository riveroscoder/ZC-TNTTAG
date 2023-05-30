package org.riveros.coder.Utils;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.Permission;

import org.riveros.coder.Managers.ArenaManager;
import org.riveros.coder.Managers.FileManager;
import org.riveros.coder.Managers.MessageManager;
import org.riveros.coder.Main.TNTTag;

public abstract class AbstractCommand {
	
	private String name;
	private String desc;
	private String args;
	private TNTTag plugin;
	private Permission perm;
	private String alias;
	private boolean useperms;
	
	public AbstractCommand(TNTTag plugin, String name, String desc, String args, Permission perm, boolean useperms) {
		this(plugin, name, desc, args, perm, useperms, null);
	}

	public AbstractCommand(TNTTag plugin, String name, String desc, String args, Permission perm, boolean useperms, String alias) {
		this.plugin = plugin;
		this.alias = alias;
		this.name = name;
		this.desc = desc;
		this.args = args;
		this.perm = perm;
		this.useperms = useperms;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.desc;
	}

	public String getArgs() {
		return this.args;
	}
	
	public String getAlias() {
		return alias;
	}

	public Permission getPermission() {
		return this.perm;
	}

	public void sendMessage(CommandSender sender, String s) {
		plugin.getMessageManager().sendMessage(sender, s);
	}

	public FileConfiguration getPlayerData() {
		return plugin.getFileManager().getPlayerData();
	}
	
	public FileManager getFileManager() {
		return plugin.getFileManager();
	}
	
	public MessageManager getMessageManager() {
		return plugin.getMessageManager();
	}
	
	public ArenaManager getArenaManager() {
		return plugin.getArenaManager();
	}
	
	public TNTTag getPlugin() {
		return plugin;
	}

	public abstract void onCommand(CommandSender paramCommandSender, String[] paramArrayOfString);

	public boolean usePermissions() {
		return this.useperms;
	}
}