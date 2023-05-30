package org.riveros.coder.ZCAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerWinEvent extends Event {
	Player w;
	private static final HandlerList handlers = new HandlerList();

	public PlayerWinEvent(Player winner) {
		this.w = winner;
	}

	public Player getWinner() {
		return this.w;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
