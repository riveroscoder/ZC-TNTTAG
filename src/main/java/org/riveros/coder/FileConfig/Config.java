package org.riveros.coder.FileConfig;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
	private static FileConfiguration config = null;
	private static File configFile = null;

	public static enum PlayerType {
		Player, TNT;
	}

	static String[] broadcastTimes = { "1", "2", "3", "4", "5", "10", "15", "20", "30", "60", "120" };
	static String[] roundWinCommands = { "eco give {player} 2", "another command", "you get it" };
	static String[] gameWinCommands = { "eco give {player} 3", "another command", "you get it" };
	static String[] commands = { "time" };

	public static void load() {
		config = getConfig();

		config.addDefault("BroadcastTimes", Arrays.asList(broadcastTimes));
		config.addDefault("AllowedCommands", Arrays.asList(commands));
		config.addDefault("minplayers", Integer.valueOf(2));
		config.addDefault("maxplayers", Integer.valueOf(10));
		config.addDefault("usepermissions", Boolean.valueOf(false));
		config.addDefault("RoundWinCommand", Boolean.valueOf(false));
		config.addDefault("RoundWinCommands", Arrays.asList(roundWinCommands));
		config.addDefault("GameWinCommand", Boolean.valueOf(false));
		config.addDefault("GameWinCommands", Arrays.asList(gameWinCommands));
		getConfig().options().copyDefaults(true);
		save();
	}

	public static void reload() {
		if (configFile == null) {
			configFile = new File("plugins/ZC-TNTTAG/config.yml");
		}
		config = YamlConfiguration.loadConfiguration(configFile);
	}

	public static FileConfiguration getConfig() {
		if (config == null) {
			reload();
		}
		return config;
	}

	public static void save() {
		if ((config == null) || (configFile == null)) {
			return;
		}
		try {
			config.save(configFile);
		} catch (IOException ex) {
			Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Could not save configFile to " + configFile, ex);
		}
	}

	public static Integer getSpeed(PlayerType type) {
		int speed = 1;
		switch (type) {
			case Player:
				speed = getConfig().getInt("Speed.Players") - 1;
				break;
			case TNT:
				speed = getConfig().getInt("Speed.TNTs") - 1;
		}
		return Integer.valueOf(speed);
	}

	public static boolean checkforupdate() {
		return getConfig().getBoolean("checkforupdates");
	}

	public static String getLineNumber(Integer i) {
		int i1 = i.intValue() + 1;
		return getConfig().getString("signs.line" + i1);
	}

	public static void executeRoundWinCommand(Player player) {
		if (getConfig().getBoolean("RoundWinCommad")) {
			for (String s : getConfig().getStringList("RoundWinCommands")) {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), s.replace("{player}", player.getName()));
			}
		}
	}

	public static void executeGameWinCommand(Player player) {
		if (getConfig().getBoolean("GameWinCommad")) {
			for (String s : getConfig().getStringList("GameWinCommands")) {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), s.replace("{player}", player.getName()));
			}
		}
	}
}
