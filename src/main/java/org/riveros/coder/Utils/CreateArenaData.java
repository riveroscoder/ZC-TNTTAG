package org.riveros.coder.Utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import org.riveros.coder.FileConfig.Messages;
import org.riveros.coder.Main.TNTTag;

public class CreateArenaData {

	private Location lobbyLocation;
	private Location arenaLocation;
	private Location spectatorsLocation;
	private Player player;
	private TNTTag plugin;

	public CreateArenaData(TNTTag plugin, Player player) {
		this.plugin = plugin;
		this.player = player;
	}

	public void setArenaLocation() {
		arenaLocation = player.getLocation();
		plugin.getMessageManager().sendMessage(player, Messages.getMessage(Message.arenaTempSaved));
	}

	public void setLobbyLocation() {
		lobbyLocation = player.getLocation();
		plugin.getMessageManager().sendMessage(player, Messages.getMessage(Message.lobbyTempSaved));
	}

	public void setSpectatorsLocation() {
		spectatorsLocation = player.getLocation();
		plugin.getMessageManager().sendMessage(player, Messages.getMessage(Message.spectatorsTempSaved));
	}

	public boolean check() {
		boolean b = false;
		if (lobbyLocation == null) {
			b = true;
			plugin.getMessageManager().sendErrorMessage(player, Messages.getMessage(Message.lobbyMissing));
		}
		if (arenaLocation == null) {
			b = true;
			plugin.getMessageManager().sendErrorMessage(player, Messages.getMessage(Message.arenaMissing));
		}
		if (spectatorsLocation == null) {
			b = true;
			plugin.getMessageManager().sendErrorMessage(player, Messages.getMessage(Message.spectatorsMissing));
		}
		return b;
	}

	public void createArena(String arenaName) {
		plugin.getArenaManager().createArena(arenaName, lobbyLocation, arenaLocation, spectatorsLocation);
		plugin.getDataManager().removeData(player);
		this.arenaLocation = null;
		this.lobbyLocation = null;
		this.plugin = null;
		this.player = null;
		this.spectatorsLocation = null;
	}
}
