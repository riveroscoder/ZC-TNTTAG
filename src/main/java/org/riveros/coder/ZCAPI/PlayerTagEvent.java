package org.riveros.coder.ZCAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerTagEvent extends Event {
	Player p;
	Player tp;
	private static final HandlerList handlers = new HandlerList();

	public PlayerTagEvent(Player tagger, Player taggedplayer) {
		this.p = tagger;
		this.tp = taggedplayer;
	}

	public Player getTagger() {
		return this.p;
	}

	public Player getTaggedPlayer() {
		return this.tp;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
