package org.riveros.coder.Main;

import java.util.HashMap;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import org.riveros.coder.Commands.CommandManager;
import org.riveros.coder.Managers.*;
import org.riveros.coder.Managers.Arena.ArenaManager;
import org.riveros.coder.Managers.Arena.Arena;
import org.riveros.coder.Managers.Arena.TempArenaDataManager;
import org.riveros.coder.Scoreboard.PlayerBoard;
import org.riveros.coder.Utils.Permissions;
import org.riveros.coder.Utils.Utils;

public class TNTTag extends JavaPlugin {

	private FileManager fileManager;
	private Permissions perms;
	private MessageManager messageManager;
	private ArenaManager arenaManager;
	private CommandManager commandManager;
	private SignManager signManager;
	private TempArenaDataManager dataManager;
	private Economy economy;
	private Logger logger;
	private boolean versionDiff = false;

	private HashMap<Player, PlayerBoard> boards = new HashMap<>();
	private boolean useEconomy = false;
	private String name;
	private String version;

	public static TNTTag instance;

	public void onEnable() {
		final long currentTime = System.currentTimeMillis();
		if (!this.getDataFolder().exists()) {
			this.getDataFolder().mkdir();
		}

		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			Bukkit.getConsoleSender().sendMessage(Utils.CCS("&aSe encontro PlaceholderAPI correctamente"));
		} else {
			Bukkit.getConsoleSender().sendMessage(Utils.CCS("&cColoca el PlaceholderAPI... ! :("));
			Bukkit.getPluginManager().disablePlugin(this);
		}

		instance = this;
		if (getServer().getPluginManager().getPlugin("Vault") != null) {
			RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
			if (rsp != null) {
				if (rsp.getProvider() != null) {
					economy = rsp.getProvider();
					useEconomy = true;
				}
			}
		}
		
		this.logger = getLogger();
		this.fileManager = new FileManager();
		this.perms = new Permissions();
		this.perms.loadPermissions(this);
		this.messageManager = new MessageManager();
		this.arenaManager = new ArenaManager(this);
		this.commandManager = new CommandManager(this);
		this.signManager = new SignManager(this);
		this.dataManager = new TempArenaDataManager(this);
		this.fileManager.setup(this);
		ListenerManager.registerEvents(this);
		getCommand("zctnt").setExecutor(commandManager);
		getCommand("zelitag").setExecutor(commandManager);
	}

	public void onDisable() {
		final long currentTime = System.currentTimeMillis();
		this.fileManager.saveConfig();
		for (Arena arena : Arena.arenaObjects) {
			arena.sendMessage("Reiniciando Arenas Disponibles");
			arenaManager.endArena(arena);
		}
		this.perms.unloadPermissions(this);
	}

	public FileManager getFileManager() {
		return fileManager;
	}
	
	public MessageManager getMessageManager() {
		return messageManager;
	}

	public ArenaManager getArenaManager() {
		return arenaManager;
	}
	
	public SignManager getSignManager() {
		return signManager;
	}
	
	public TempArenaDataManager getDataManager() {
		return dataManager;
	}
	
	public String getTNTName() {
		return name;
	}
	
	public Economy getEconomy() {
		return economy;
	}

	public HashMap<Player, PlayerBoard> getBoards() {
		return boards;
	}

	public String getVersionString() {
		return version;
	}

	public static TNTTag getInstance() {
		return instance;
	}
}
