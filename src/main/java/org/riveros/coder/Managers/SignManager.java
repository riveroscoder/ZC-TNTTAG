package org.riveros.coder.Managers;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;

import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.FileConfig.Signs;
import org.riveros.coder.Utils.TNTTagSign;

public class SignManager {
	
	public HashMap<String, String> tempSign = new HashMap<String, String>();
	private TNTTag plugin;
	
	public SignManager(TNTTag plugin) {
		this.plugin = plugin;
	}
	
	public void updateSigns(String arena) {
		for (TNTTagSign sign : TNTTagSign.signs) {
			sign.update();
		}
	}

	public TNTTagSign getSignAtLocation(Location location) {
		for (TNTTagSign sign : TNTTagSign.signs) {
			if (sign.getSignLocation().equals(location)) {
				return sign;
			}
		}
		return null;
	}

	public String getArenaOnSign(Location location) {
		for (TNTTagSign sign : TNTTagSign.signs) {
			if (sign.getSignLocation().equals(location)) {
				return sign.getArena();
			}
		}
		return null;
	}

	public void addSign(Location location, String arena, Sign sign) {
		FileConfiguration fc = Signs.getSignsData();

		int id = getAvailableSignId();

		fc.set("signs." + id, null);

		String path = "signs." + id + ".";

		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		String world = location.getWorld().getName();

		fc.set(path + "world", world);
		fc.set(path + "x", Integer.valueOf(x));
		fc.set(path + "y", Integer.valueOf(y));
		fc.set(path + "z", Integer.valueOf(z));
		fc.set(path + "line.1", sign.getLine(0));
		fc.set(path + "line.2", sign.getLine(1));
		fc.set(path + "line.3", sign.getLine(2));
		fc.set(path + "line.4", sign.getLine(3));
		fc.set(path + "arena", arena);

		new TNTTagSign(plugin, location, Integer.valueOf(id), arena, sign.getLine(0), sign.getLine(1), sign.getLine(2), sign.getLine(3));

		Signs.save();
	}

	public void loadSigns() {
		FileConfiguration fc = Signs.getSignsData();
		if (fc.getString("signs") != null) {
			for (String id : fc.getConfigurationSection("signs").getKeys(false)) {
				World world = Bukkit.getWorld(fc.getString("signs." + id + ".world"));
				int x = fc.getInt("signs." + id + ".x");
				int y = fc.getInt("signs." + id + ".y");
				int z = fc.getInt("signs." + id + ".z");
				Location location = new Location(world, x, y, z);

				String arena = fc.getString("signs." + id + ".arena");
				String line1 = fc.getString("signs." + id + ".line.1");
				String line2 = fc.getString("signs." + id + ".line.2");
				String line3 = fc.getString("signs." + id + ".line.3");
				String line4 = fc.getString("signs." + id + ".line.4");

				new TNTTagSign(plugin, location, Integer.valueOf(Integer.parseInt(id)), arena, line1, line2, line3, line4);
			}
		}
	}

	private int getAvailableSignId() {
		ArrayList<Integer> signIds = new ArrayList<Integer>();
		for (TNTTagSign tagSign : TNTTagSign.signs) {
			signIds.add(tagSign.getId());
		}
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			if (!signIds.contains(Integer.valueOf(i))) {
				return i;
			}
		}
		return 0;
	}
}
