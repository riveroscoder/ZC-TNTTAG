package org.riveros.coder.Events;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.riveros.coder.Utils.Utils;
import org.riveros.coder.ZCAPI.PlayerTagEvent;
import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.FileConfig.Messages;
import org.riveros.coder.Utils.FireworkEffectPlayer;
import org.riveros.coder.Utils.Message;

public class EntityDamageByEntityListener implements Listener {

	FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
	private TNTTag plugin;

	public EntityDamageByEntityListener(TNTTag plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void dmg(EntityDamageByEntityEvent event) throws Exception {
		if (((event.getEntity() instanceof Player)) && ((event.getDamager() instanceof Player))) {
			Player damager = (Player) event.getDamager();
			Player victim = (Player) event.getEntity();
			if (plugin.getArenaManager().isInGame(victim)) {
				if (plugin.getArenaManager().isTNT(damager)) {
					plugin.getArenaManager().addTNTPlayer(victim);
					plugin.getArenaManager().removeTNTPlayer(damager);

					plugin.getMessageManager().sendInGamePlayersMessage(Messages.getMessage(Message.playerIsIt).replace("{player}", victim.getName()), plugin.getArenaManager().get(victim));

					Bukkit.getServer().getPluginManager().callEvent(new PlayerTagEvent(damager, victim));

					int tags = plugin.getFileManager().getPlayerData().getInt(damager.getName() + ".tags");
					int taggeds = plugin.getFileManager().getPlayerData().getInt(victim.getName() + ".taggeds");

					plugin.getFileManager().getPlayerData().set(damager.getName() + ".tags", Integer.valueOf(tags + 1));
					plugin.getFileManager().getPlayerData().set(victim.getName() + ".taggeds", Integer.valueOf(taggeds + 1));

					for (PotionEffect effect : damager.getActivePotionEffects()) {
						damager.removePotionEffect(effect.getType());
					}

					for (PotionEffect effect : victim.getActivePotionEffects()) {
						victim.removePotionEffect(effect.getType());
					}

					victim.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 4));
					damager.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 3));

					victim.getInventory().setHelmet(new ItemStack(Material.TNT, 1));
					damager.getInventory().setHelmet(new ItemStack(Material.AIR, 1));

					victim.setPlayerListName(Utils.CCS("&c[T] " + victim.getName()));
					damager.setPlayerListName(Utils.CCS("&a" + damager.getName()));

					damager.getInventory().setItem(0, new ItemStack(Material.COMPASS, 1));
					damager.getInventory().setItem(1, new ItemStack(Material.AIR, 1));
					damager.getInventory().setItem(2, new ItemStack(Material.AIR, 1));
					damager.getInventory().setItem(3, new ItemStack(Material.AIR, 1));
					damager.getInventory().setItem(4, new ItemStack(Material.AIR, 1));
					damager.getInventory().setItem(5, new ItemStack(Material.AIR, 1));
					damager.getInventory().setItem(6, new ItemStack(Material.AIR, 1));
					damager.getInventory().setItem(7, new ItemStack(Material.AIR, 1));
					damager.getInventory().setItem(8, new ItemStack(Material.AIR, 1));
					damager.getInventory().setItem(9, new ItemStack(Material.AIR, 1));

					victim.getInventory().setItem(0, new ItemStack(Material.TNT, 64));
					victim.getInventory().setItem(1, new ItemStack(Material.TNT, 64));
					victim.getInventory().setItem(2, new ItemStack(Material.TNT, 64));
					victim.getInventory().setItem(3, new ItemStack(Material.TNT, 64));
					victim.getInventory().setItem(4, new ItemStack(Material.TNT, 64));
					victim.getInventory().setItem(5, new ItemStack(Material.TNT, 64));
					victim.getInventory().setItem(6, new ItemStack(Material.TNT, 64));
					victim.getInventory().setItem(7, new ItemStack(Material.TNT, 64));
					victim.getInventory().setItem(8, new ItemStack(Material.TNT, 64));
					victim.getInventory().setItem(9, new ItemStack(Material.TNT, 64));

					FireworkEffect effect = FireworkEffect.builder().withColor(Color.RED).with(FireworkEffect.Type.CREEPER).build();
					this.fplayer.playFirework(victim.getWorld(), victim.getLocation(), effect);

					victim.setHealth(20.0D);
				} else {
					victim.setHealth(20.0D);
				}
			}
		}
	}
}
