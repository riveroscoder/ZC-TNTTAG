package org.riveros.coder.Scoreboard;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.riveros.coder.FileConfig.ScoreB;
import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.Managers.FileManager;

public class ScoreboardListener implements Listener {


    FileConfiguration yml = ScoreB.getScoreboard();
    private TNTTag plugin;

    public ScoreboardListener(TNTTag plugin) {
        this.plugin = plugin;
    }

    @EventHandler(
            priority = EventPriority.HIGH
    )
    public void onJoinBoard(PlayerJoinEvent e) {
        Player p233 = e.getPlayer();
        if (yml.getBoolean("ENABLED") && yml.getStringList("WORLDS").contains(e.getPlayer().getWorld().getName())) {
            new PlayerBoard(TNTTag.getInstance(),
                    p233,
                    yml.getStringList("IN-GAME.LINES.text"),
                    yml.getStringList("TITLE.LINES"),
                    yml.getInt("TITLE.DELAY")
            ).remove();
            new PlayerBoard(TNTTag.getInstance(),
                    p233,
                    yml.getStringList("IN-LOBBY.LINES.text"),
                    yml.getStringList("TITLE.LINES"),
                    yml.getInt("TITLE.DELAY")
            );
        }
    }

    @EventHandler(
            priority = EventPriority.LOWEST
    )
    public void onQuitBoard(PlayerQuitEvent e) {
        Player p233 = e.getPlayer();
        ((PlayerBoard) TNTTag.getInstance().getBoards().get(p233)).remove();
    }

}
