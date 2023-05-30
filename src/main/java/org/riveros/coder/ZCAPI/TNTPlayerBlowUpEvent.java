package org.riveros.coder.ZCAPI;

import java.util.ArrayList;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TNTPlayerBlowUpEvent extends Event {
	private ArrayList<String> playersThatBlewUp = new ArrayList<String>();
	private static final HandlerList handlers = new HandlerList();

	public TNTPlayerBlowUpEvent(ArrayList<String> list) {
		this.playersThatBlewUp = list;
	}

	public ArrayList<String> getBLownUpPlayers() {
		return this.playersThatBlewUp;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
