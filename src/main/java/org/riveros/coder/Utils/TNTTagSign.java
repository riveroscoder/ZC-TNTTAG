package org.riveros.coder.Utils;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import org.riveros.coder.Main.TNTTag;

public class TNTTagSign {
	
	public static ArrayList<TNTTagSign> signs = new ArrayList<TNTTagSign>();
	private String arena;
	private Location signLocation;
	private Integer id;
	private TNTTag plugin;
	String[] signLines = new String[5];

	public TNTTagSign(TNTTag plugin, Location location, Integer id, String arena, String line1, String line2, String line3, String line4) {
		this.plugin = plugin;
		this.arena = arena;
		this.id = id;
		this.signLocation = location;
		this.signLines[0] = line1;
		this.signLines[1] = line2;
		this.signLines[2] = line3;
		this.signLines[3] = line4;
		signs.add(this);
	}

	public String getArena() {
		return this.arena;
	}

	public Location getSignLocation() {
		return this.signLocation;
	}

	public void update() {
		World w = this.signLocation.getWorld();
		Block b = w.getBlockAt(this.signLocation);
		if ((b.getType() == Material.SIGN_POST) || (b.getType() == Material.WALL_SIGN)) {
			Sign sign = (Sign) b.getState();
			for (int x = 0; x < 4; x++) {
				if (getLine(Integer.valueOf(x + 1)) != null) {
					sign.setLine(x, getLine(Integer.valueOf(x + 1)).replace("&", "§").replace("{max_players}", plugin.getArenaManager().getArena(this.arena).getMaxPlayers() + "").replace("{min_players}", plugin.getArenaManager().getArena(this.arena).getMinPlayers() + "").replace("{players}", plugin.getArenaManager().getArena(this.arena).getPlayers().size() + ""));
				}
			}
			sign.update();
		}
	}

	public String getLine(Integer number) {
		return this.signLines[(number.intValue() - 1)];
	}

	public Integer getId() {
		return this.id;
	}
}
