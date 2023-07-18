package org.riveros.coder.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.Managers.Arena.ArenaManager;
import org.riveros.coder.Utils.Utils;

public class AsyncPlayerChatEventListener implements Listener {

    private TNTTag plugin;

    public AsyncPlayerChatEventListener(TNTTag plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void AsyncPlayerChatEvent(AsyncPlayerChatEvent e){
        Player sender = e.getPlayer();
        String message = e.getMessage();
        if(plugin.getArenaManager().isInGame(sender)){
            for(Player p : plugin.getArenaManager().getArena(plugin.getArenaManager().get(sender).getName()).getPlayers()){
                if(plugin.getArenaManager().getArena(plugin.getArenaManager().get(sender).getName()).getTNTPlayers().contains(sender)){
                    Utils.CC(p, "&7[En Juego] &c(TNT) " + sender.getName() + "&7: &f" + message);
                } else {
                    Utils.CC(p, "&7[En Juego] &a" + sender.getName() + "&7: &f" + message);
                }

            }
        } else {
            for(Player p : sender.getWorld().getPlayers()){
                Utils.CC(p, "&7[En Lobby] &6(0&6)&f" + sender.getName() + "&7: &f" + message);
            }
        }
        e.setCancelled(true);
    }

}
