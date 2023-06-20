package org.riveros.coder.Managers;

import org.bukkit.plugin.PluginManager;

import org.riveros.coder.Events.*;
import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.Scoreboard.ScoreboardListener;

public class ListenerManager {
	
	public static void registerEvents(TNTTag plugin) {
		PluginManager pm = plugin.getServer().getPluginManager();

		pm.registerEvents(new ScoreboardListener(plugin), plugin);

		pm.registerEvents(new BlockBreakListener(plugin), plugin);
		pm.registerEvents(new AsyncPlayerChatEventListener(plugin), plugin);
		pm.registerEvents(new BlockPlaceListener(plugin), plugin);
		pm.registerEvents(new DropItemListener(plugin), plugin);
		pm.registerEvents(new EntityDamageByEntityListener(plugin), plugin);
		pm.registerEvents(new EntityDamageListener(plugin), plugin);
		pm.registerEvents(new FoodLevelChangeListener(plugin), plugin);
		pm.registerEvents(new InventoryClickListener(plugin), plugin);
		pm.registerEvents(new PlayerCommandPreprocessListener(plugin), plugin);
		pm.registerEvents(new PlayerInteractListener(plugin), plugin);
		pm.registerEvents(new PlayerQuitListener(plugin), plugin);
		pm.registerEvents(new PlayerKickListener(plugin), plugin);
	}
}
