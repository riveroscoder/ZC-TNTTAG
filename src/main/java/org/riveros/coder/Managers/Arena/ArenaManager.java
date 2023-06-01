package org.riveros.coder.Managers.Arena;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.FileConfig.GameData;
import org.riveros.coder.FileConfig.Messages;
import org.riveros.coder.Managers.InventoryManager;
import org.riveros.coder.Utils.Message;
import org.riveros.coder.Utils.Utils;

public class ArenaManager {
	
	private TNTTag plugin;

	public ArenaManager(TNTTag plugin) {
		this.plugin = plugin;
	}
	
	public Arena getArena(String name) {
		for (Arena a : Arena.arenaObjects) {
			if (a.getName().equalsIgnoreCase(name)) {
				return a;
			}
		}
		return null;
	}

	public void addPlayers(Player player, String arenaName) {
		if (player.isInsideVehicle()) {
			player.leaveVehicle();
		}
		if (player.isSleeping()) {
			player.kickPlayer(Messages.getMessage(Message.joinedFromBed));
			return;
		}
		if (getArena(arenaName) != null) {
			Arena arena = getArena(arenaName);
			if (!arena.isFull()) {
				if (!arena.isInGame()) {
					InventoryManager.saveInventory(player);
					if (arena.getLobbyLocation() != null) {
						player.teleport(arena.getLobbyLocation());

						arena.getPlayers().add(player);

						arena.getAlivePlayers().add(player);
						int playersLeft = arena.getMinPlayers() - arena.getPlayers().size();

						Utils.CCT(player, "", "&fIngresaste a la Arena &a" + arenaName);
						arena.sendMessage(Messages.getMessage(Message.joinedGame).replace("{player}", player.getName()).replace("{size}", arena.getPlayers().size() + "").replace("{max_players}", arena.getMaxPlayers() + ""));
						if ((playersLeft == 0) && (!arena.runningCountdown())) {
							startArena(arenaName);
						}
						plugin.getSignManager().updateSigns(arenaName);
					}
				} else {
					player.sendMessage(ChatColor.RED + Messages.getMessage(Message.arenaAlreadyStarted));
				}
			} else {
				player.sendMessage(ChatColor.RED + Messages.getMessage(Message.fullArena));
			}
		} else {
			player.sendMessage(ChatColor.RED + Messages.getMessage(Message.arenaNotFound));
		}
	}

	public boolean isInGame(Player player1) {
		for (Arena arena : Arena.arenaObjects) {
			for (Player player : arena.getPlayers()) {
				if (player.getName().equalsIgnoreCase(player1.getName())) {
					return true;
				}
			}
		}
		return false;
	}

	public Arena get(Player player1) {
		for (Arena arena : Arena.arenaObjects) {
			for (Player player : arena.getPlayers()) {
				if (player.getName().equalsIgnoreCase(player1.getName())) {
					return arena;
				}
			}
		}
		return null;
	}

	public boolean isTNT(Player player1) {
		for (Arena arena : Arena.arenaObjects) {
			for (Player player : arena.getTNTPlayers()) {
				if (player.getName().equalsIgnoreCase(player1.getName())) {
					return true;
				}
			}
		}
		return false;
	}

	public void removePlayer(Player player) {
		Arena arena = get(player);

		player.getInventory().clear();
		InventoryManager.restoreInventory(player);

		arena.getPlayers().remove(player);
		if (arena.getTNTPlayers().contains(player)) {
			arena.getTNTPlayers().remove(player);
		}
		if (arena.getAlivePlayers().contains(player)) {
			arena.getAlivePlayers().remove(player);
		}
		plugin.getSignManager().updateSigns(arena.getName());

		arena.removeBoard(player);
		if (arena.getPlayers().size() == 1) {
			if (arena.isInGame()) {
				arena.sendMessage("The last player left!");
				endArena(arena);
			} else {
				arena.getCountdownManager().cancelTask();
			}
		}
	}

	public void removeTNTPlayer(Player player) {
		Arena arena = get(player);

		arena.getTNTPlayers().remove(player);
		arena.getAlivePlayers().add(player);
	}

	public void addTNTPlayer(Player player) {
		Arena arena = get(player);

		arena.getTNTPlayers().add(player);
		arena.getAlivePlayers().remove(player);
	}

	public void startArena(String arenaName) {
		if (getArena(arenaName) != null) {
			Arena arena = getArena(arenaName);
			if (arena.getPlayers().size() >= 2) {
				arena.getCountdownManager().startGame(50);
			}
		}
	}

	public void forceStartArena(String arenaName, CommandSender player) {
		Arena arena = getArena(arenaName);
		if (arena.getPlayers().size() >= 2) {
			if (!arena.isInGame()) {
				plugin.getMessageManager().sendMessage(player, Messages.getMessage(Message.forceStarting));
				arena.getCountdownManager().cancelTask();
				arena.getCountdownManager().startGame(10);
			} else {
				plugin.getMessageManager().sendErrorMessage(player, Messages.getMessage(Message.forceStartAlreadyStarted));
			}
		} else {
			plugin.getMessageManager().sendErrorMessage(player, Messages.getMessage(Message.minTwoPlayers));
		}
	}

	public void forceEndArena(String arenaName, Player player) {
		Arena arena = getArena(arenaName);
		if (arena.isInGame()) {
			plugin.getMessageManager().sendMessage(player, Messages.getMessage(Message.forceEnding));
			arena.sendMessage(Messages.getMessage(Message.forceEndKicked));
			endArena(arena);
		} else {
			plugin.getMessageManager().sendErrorMessage(player, Messages.getMessage(Message.arenaHasAlreadyStarted));
		}
	}

	public void endArena(Arena arena) {
		if (arena.runningCountdown()) {
			arena.getCountdownManager().cancelTask();
		}
		arena.endArena();
	}

	public void loadArenas() {
		FileConfiguration fc = GameData.getGameData();
		if (fc.getString("arenas") != null) {
			for (String keys : fc.getConfigurationSection("arenas").getKeys(false)) {
				World joinWorld = Bukkit.getWorld(fc.getString("arenas." + keys + ".lobby.world"));
				int joinX = fc.getInt("arenas." + keys + ".lobby.x");
				int joinY = fc.getInt("arenas." + keys + ".lobby.y");
				int joinZ = fc.getInt("arenas." + keys + ".lobby.z");
				float joinpitch = fc.getInt("arenas." + keys + ".lobby.pitch");
				float joinyaw = fc.getInt("arenas." + keys + ".lobby.yaw");
				Location joinLocation = new Location(joinWorld, joinX, joinY, joinZ, joinyaw, joinpitch);

				World startWorld = Bukkit.getWorld(fc.getString("arenas." + keys + ".arena.world"));
				int startX = fc.getInt("arenas." + keys + ".arena.x");
				int startY = fc.getInt("arenas." + keys + ".arena.y");
				int startZ = fc.getInt("arenas." + keys + ".arena.z");
				float startpitch = fc.getInt("arenas." + keys + ".arena.pitch");
				float startyaw = fc.getInt("arenas." + keys + ".arena.yaw");
				Location startLocation = new Location(startWorld, startX, startY, startZ, startyaw, startpitch);

				World endWorld = Bukkit.getWorld(fc.getString("arenas." + keys + ".spec.world"));
				int endX = fc.getInt("arenas." + keys + ".spec.x");
				int endY = fc.getInt("arenas." + keys + ".spec.y");
				int endZ = fc.getInt("arenas." + keys + ".spec.z");
				float endpitch = fc.getInt("arenas." + keys + ".spec.pitch");
				float endyaw = fc.getInt("arenas." + keys + ".spec.yaw");
				Location endLocation = new Location(endWorld, endX, endY, endZ, endyaw, endpitch);

				int maxPlayers = plugin.getFileManager().getMaxPlayers();
				int minPlayers = plugin.getFileManager().getMinPlayers();

				new Arena(plugin, keys, joinLocation, startLocation, endLocation, maxPlayers, minPlayers);
			}
		}
	}

	public void createArena(String arenaName, Location joinLocation, Location startLocation, Location endLocation) {
		int maxPlayers = plugin.getFileManager().getMaxPlayers();
		int minPlayers = plugin.getFileManager().getMinPlayers();

		new Arena(plugin, arenaName, joinLocation, startLocation, endLocation, maxPlayers, minPlayers);

		FileConfiguration fc = GameData.getGameData();

		fc.set("arenas." + arenaName, null);

		String path = "arenas." + arenaName + ".";

		int joinx = joinLocation.getBlockX();
		int joiny = joinLocation.getBlockY();
		int joinz = joinLocation.getBlockZ();
		float joinpitch = joinLocation.getPitch();
		float joinyaw = joinLocation.getYaw();
		String joinworld = joinLocation.getWorld().getName();

		fc.set(path + "lobby.world", joinworld);
		fc.set(path + "lobby.x", Integer.valueOf(joinx));
		fc.set(path + "lobby.y", Integer.valueOf(joiny));
		fc.set(path + "lobby.z", Integer.valueOf(joinz));
		fc.set(path + "lobby.pitch", Float.valueOf(joinpitch));
		fc.set(path + "lobby.yaw", Float.valueOf(joinyaw));

		int startx = startLocation.getBlockX();
		int starty = startLocation.getBlockY();
		int startz = startLocation.getBlockZ();
		float startpitch = startLocation.getPitch();
		float startyaw = startLocation.getYaw();
		String startworld = startLocation.getWorld().getName();

		fc.set(path + "arena.world", startworld);
		fc.set(path + "arena.x", Integer.valueOf(startx));
		fc.set(path + "arena.y", Integer.valueOf(starty));
		fc.set(path + "arena.z", Integer.valueOf(startz));
		fc.set(path + "arena.pitch", Float.valueOf(startpitch));
		fc.set(path + "arena.yaw", Float.valueOf(startyaw));

		int endx = endLocation.getBlockX();
		int endy = endLocation.getBlockY();
		int endz = endLocation.getBlockZ();
		float endpitch = endLocation.getPitch();
		float endyaw = endLocation.getYaw();
		String endworld = endLocation.getWorld().getName();

		fc.set(path + "spec.world", endworld);
		fc.set(path + "spec.x", Integer.valueOf(endx));
		fc.set(path + "spec.y", Integer.valueOf(endy));
		fc.set(path + "spec.z", Integer.valueOf(endz));
		fc.set(path + "spec.pitch", Float.valueOf(endpitch));
		fc.set(path + "spec.yaw", Float.valueOf(endyaw));

		fc.set(path + "maxPlayers", Integer.valueOf(maxPlayers));
		fc.set(path + "minPlayers", Integer.valueOf(minPlayers));

		plugin.getFileManager().saveConfig();
	}
}
