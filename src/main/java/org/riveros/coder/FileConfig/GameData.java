package org.riveros.coder.FileConfig;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class GameData {
	private static FileConfiguration config = null;
	private static File configFile = null;

	public static void load() {
		config = getGameData();
		getGameData().options().copyDefaults(true);
		save();
	}

	public static void reload() {
		if (configFile == null) {
			configFile = new File("plugins/ZC-TNTTAG/GameData.yml");
		}
		config = YamlConfiguration.loadConfiguration(configFile);
	}

	public static FileConfiguration getGameData() {
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
}
