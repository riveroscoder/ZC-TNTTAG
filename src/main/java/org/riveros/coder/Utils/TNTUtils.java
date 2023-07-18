package org.riveros.coder.Utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TNTUtils {

    public static void update(Player player, Boolean istnt){
        if(istnt){
            player.setPlayerListName(Utils.CCS("&c[T] " + player.getName()));
            player.getInventory().setHelmet(new ItemStack(Material.TNT, 1));
            player.getInventory().setItem(0, new ItemStack(Material.TNT, 64));
            player.getInventory().setItem(1, new ItemStack(Material.TNT, 64));
            player.getInventory().setItem(2, new ItemStack(Material.TNT, 64));
            player.getInventory().setItem(3, new ItemStack(Material.TNT, 64));
            player.getInventory().setItem(4, new ItemStack(Material.TNT, 64));
            player.getInventory().setItem(5, new ItemStack(Material.TNT, 64));
            player.getInventory().setItem(6, new ItemStack(Material.TNT, 64));
            player.getInventory().setItem(7, new ItemStack(Material.TNT, 64));
            player.getInventory().setItem(8, new ItemStack(Material.TNT, 64));
            player.getInventory().setItem(9, new ItemStack(Material.TNT, 64));
            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 4));
        } else {
            player.setPlayerListName(Utils.CCS("&7" + player.getName()));
            player.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
            player.getInventory().setItem(0, new ItemStack(Material.COMPASS, 1));
            player.getInventory().setItem(1, new ItemStack(Material.AIR, 1));
            player.getInventory().setItem(2, new ItemStack(Material.AIR, 1));
            player.getInventory().setItem(3, new ItemStack(Material.AIR, 1));
            player.getInventory().setItem(4, new ItemStack(Material.AIR, 1));
            player.getInventory().setItem(5, new ItemStack(Material.AIR, 1));
            player.getInventory().setItem(6, new ItemStack(Material.AIR, 1));
            player.getInventory().setItem(7, new ItemStack(Material.AIR, 1));
            player.getInventory().setItem(8, new ItemStack(Material.AIR, 1));
            player.getInventory().setItem(9, new ItemStack(Material.AIR, 1));
        }
    }

    public static void update(Player damager, Player victim){

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
    }

}
