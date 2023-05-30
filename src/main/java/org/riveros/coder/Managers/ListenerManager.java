package org.riveros.coder.Managers;

import org.bukkit.plugin.PluginManager;

import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.Events.BlockBreakListener;
import org.riveros.coder.Events.DropItemListener;
import org.riveros.coder.Events.EntityDamageByEntityListener;
import org.riveros.coder.Events.EntityDamageListener;
import org.riveros.coder.Events.FoodLevelChangeListener;
import org.riveros.coder.Events.InventoryClickListener;
import org.riveros.coder.Events.PlayerCommandPreprocessListener;
import org.riveros.coder.Events.PlayerInteractListener;
import org.riveros.coder.Events.PlayerKickListener;
import org.riveros.coder.Events.PlayerQuitListener;

public class ListenerManager {
	
	public static void registerEvents(TNTTag plugin) {
		PluginManager pm = plugin.getServer().getPluginManager();
		pm.registerEvents(new BlockBreakListener(plugin), plugin);
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
