package org.riveros.coder.FileConfig;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Signs {
	private static FileConfiguration signs = null;
	private static File signsFile = null;

	public static void load() {
		signs = getSignsData();
		getSignsData().options().copyDefaults(true);
		save();
	}

	public static void reload() {
		if (signsFile == null) {
			signsFile = new File("plugins/ZC-TNTTAG/Signs.yml");
		}
		signs = YamlConfiguration.loadConfiguration(signsFile);
	}

	public static FileConfiguration getSignsData() {
		if (signs == null) {
			reload();
		}
		return signs;
	}

	public static void save() {
		if ((signs == null) || (signsFile == null)) {
			return;
		}
		try {
			signs.save(signsFile);
		} catch (IOException ex) {
			Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Could not save configFile to " + signsFile, ex);
		}
	}
}
