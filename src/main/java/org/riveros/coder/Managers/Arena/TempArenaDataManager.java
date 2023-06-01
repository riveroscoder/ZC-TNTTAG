package org.riveros.coder.Managers.Arena;

import java.util.Map;

import org.bukkit.entity.Player;

import com.google.common.collect.Maps;
import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.Managers.Arena.CreateArenaData;

public class TempArenaDataManager {
	
	private TNTTag plugin;
	private Map<String, CreateArenaData> data;
	
	public TempArenaDataManager(TNTTag plugin) {
		this.plugin = plugin;
		data = Maps.newHashMap();
	}
	
	public CreateArenaData getData(Player player) {
		if(data.get(player.getName()) != null) {
			return data.get(player.getName());
		}
		
		CreateArenaData data1 = new CreateArenaData(plugin, player);
		data.put(player.getName(), data1);
		return data1;
	}
	
	public void removeData(Player player) {
		data.remove(player.getName());
	}

}
