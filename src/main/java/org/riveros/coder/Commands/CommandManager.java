package org.riveros.coder.Commands;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.riveros.coder.Commands.Cmds.*;
import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.Utils.AbstractTagAdminCommands;
import org.riveros.coder.Utils.AbstractTagCommands;
import org.riveros.coder.Utils.AbstractTagSetupCommands;
import org.riveros.coder.Utils.Permissions;

public class CommandManager implements CommandExecutor {

	private TNTTag plugin;
	private ArrayList<AbstractTagCommands> cmds = new ArrayList<AbstractTagCommands>();
	private ArrayList<AbstractTagAdminCommands> adminCmds = new ArrayList<AbstractTagAdminCommands>();
	private ArrayList<AbstractTagSetupCommands> setupCmds = new ArrayList<AbstractTagSetupCommands>();

	public CommandManager(TNTTag plugin) {
		this.plugin = plugin;
		this.cmds.add(new joinCmd(plugin));
		this.cmds.add(new leaveCmd(plugin));
		this.cmds.add(new coinsCmd(plugin));
		this.cmds.add(new statsCmd(plugin));
		this.cmds.add(new transferCmd(plugin));
		this.cmds.add(new listArenasCmd(plugin));

		this.adminCmds.add(new addCmd(plugin));
		this.adminCmds.add(new removeCmd(plugin));
		this.adminCmds.add(new resetStatsCmd(plugin));
		this.adminCmds.add(new forceStartCmd(plugin));
		this.adminCmds.add(new reloadCmd(plugin));
		this.adminCmds.add(new setspawnCmd(plugin));

		this.setupCmds.add(new setLobbyCmd(plugin));
		this.setupCmds.add(new setSpectatorsCmd(plugin));
		this.setupCmds.add(new setArenaPointCmd(plugin));
		this.setupCmds.add(new createArenaCmd(plugin));
		this.setupCmds.add(new ArenaCmd(plugin));
		this.setupCmds.add(new deleteArenaCmd(plugin));
		this.setupCmds.add(new createSignCmd(plugin));
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
  		if ((cmd.getName().equalsIgnoreCase("zctnt")) || (cmd.getName().equalsIgnoreCase("zelitag"))) {
			if (args.length == 0) {
				showHelp(sender);
				return true;
			}
			ArrayList<String> a = new ArrayList<String>(Arrays.asList(args));
			a.remove(0);
			if (args[0].equalsIgnoreCase("game")) {
				if (args.length != 1) {
					for (AbstractTagAdminCommands c : this.adminCmds) {
						if (c.getName().equalsIgnoreCase(args[1])) {
							if (c.usePermissions() ? (sender.hasPermission(c.getPermission())) || (sender.hasPermission(new Permissions().all)) : !c.usePermissions()) {
								a.remove(0);
								if (args.length != 1) {
									try {
										c.onCommand(sender, (String[]) a.toArray(new String[a.size()]));
									} catch (Exception e) {
										sender.sendMessage(ChatColor.RED + "An error has occurred.");
										e.printStackTrace();
									}
									return true;
								}
								plugin.getMessageManager().sendInsuficientArgs(sender, c.getName(), c.getArgs());
							} else {
								sender.sendMessage(ChatColor.RED + "You do not have permission to perform this command!");
								return true;
							}
						}
					}
					plugin.getMessageManager().sendErrorMessage(sender, "Invalid Command!");
					return true;
				}
				showAdminHelp(sender);
			} else if (args[0].equalsIgnoreCase("setup")) {
				if (!(sender instanceof Player)) {
					plugin.getMessageManager().isConsole(sender);
					return false;
				}
				if (args.length != 1) {
					for (AbstractTagSetupCommands c : this.setupCmds) {
						if (c.getName().equalsIgnoreCase(args[1])) {
							if (c.usePermissions() ? (sender.hasPermission(c.getPermission())) || (sender.hasPermission(new Permissions().all)) : !c.usePermissions()) {
								a.remove(0);
								if (args.length != 1) {
									try {
										c.onCommand(sender, (String[]) a.toArray(new String[a.size()]));
									} catch (Exception e) {
										sender.sendMessage(ChatColor.RED + "An error has occurred.");
										e.printStackTrace();
									}
									return true;
								}
								plugin.getMessageManager().sendInsuficientArgs(sender, c.getName(), c.getArgs());
							} else {
								sender.sendMessage(ChatColor.RED + "You do not have permission to perform this command!");
								return true;
							}
						}
					}
					plugin.getMessageManager().sendErrorMessage(sender, "Invalid Command!");
					return true;
				}
				showCreateHelp(sender);
			} else {
				if (!(sender instanceof Player)) {
					plugin.getMessageManager().isConsole(sender);
					return false;
				}
				for (AbstractTagCommands c : this.cmds) {
					if ((c.getName().equalsIgnoreCase(args[0])) || (c.getAlias().equalsIgnoreCase(args[0]))) {
						if (c.usePermissions() ? (sender.hasPermission(c.getPermission())) || (sender.hasPermission(new Permissions().all)) : !c.usePermissions()) {
							if (args.length != 0) {
								try {
									c.onCommand(sender, (String[]) a.toArray(new String[a.size()]));
								} catch (Exception e) {
									sender.sendMessage(ChatColor.RED + "An error has occurred.");
									e.printStackTrace();
								}
								return true;
							}
							plugin.getMessageManager().sendInsuficientArgs(sender, c.getName(), c.getArgs());
						} else {
							sender.sendMessage(ChatColor.RED + "You do not have permission to perform this command!");
							return true;
						}
					}
				}
				plugin.getMessageManager().sendErrorMessage(sender, "Invalid Command!");
				return true;
			}
		} else {
			System.out.println("Unknown Error");
		}
		return true;
	}

	public void showHelp(CommandSender sender) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ("&e&LSISTEMA DE TNTTAG DE ZELICRAFT NETWORK")));
		sender.sendMessage(" ");
		for (AbstractTagCommands c : this.cmds) {
			if(!(c.getName() != "createarena" || c.getName() != "setlobby" || c.getName() != "setarena" || c.getName() != "setspawn")){
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ("&b/zelitag &6" + c.getName() + (c.getArgs() == null ? " " : new StringBuilder(" ").append(c.getArgs()).append(" ").toString()))));
			}
		}
		sender.sendMessage(" ");
	}

	public void showAdminHelp(CommandSender sender) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ("&e&LSISTEMA DE TNTTAG DE ZELICRAFT NETWORK")));
		sender.sendMessage(" ");
		for (AbstractTagAdminCommands c : this.adminCmds) {
			if(!(c.getName() != "createarena" || c.getName() != "setlobby" || c.getName() != "setarena" || c.getName() != "setspawn")){
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ("&b/zelitag game &6" + c.getName() + (c.getArgs() == null ? " " : new StringBuilder(" ").append(c.getArgs()).append(" ").toString()))));
			}
		}
		sender.sendMessage(" ");	}

	public void showCreateHelp(CommandSender sender) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ("&e&LSISTEMA DE TNTTAG DE ZELICRAFT NETWORK")));
		sender.sendMessage(" ");
		for (AbstractTagSetupCommands c : this.setupCmds) {
			if(!(c.getName() != "createarena" || c.getName() != "setlobby" || c.getName() != "setarena" || c.getName() != "setspawn")){
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ("&b/zelitag setup &6" + c.getName() + (c.getArgs() == null ? " " : new StringBuilder(" ").append(c.getArgs()).append(" ").toString()))));
			}
		}
		sender.sendMessage(" ");	}
}
