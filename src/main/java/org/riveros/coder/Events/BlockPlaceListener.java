package org.riveros.coder.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.riveros.coder.Main.TNTTag;

public class BlockPlaceListener implements Listener {

    private TNTTag plugin;

    public BlockPlaceListener(TNTTag plugin) {
        this.plugin = plugin;
    }

    public void BlockPlace(BlockPlaceEvent e){
        Player player = e.getPlayer();
        if (plugin.getArenaManager().isInGame(player)) {
            e.setCancelled(true);
        } else if(player.isOp() && player.isSneaking()) {
            e.setCancelled(false);
        }
    }

}
