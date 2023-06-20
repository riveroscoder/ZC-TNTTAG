package org.riveros.coder.Scoreboard;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.Utils.Utils;

import java.util.List;

public class PlayerBoard {

    private TNTTag plugin;
    private Player player;
    private Scoreboard board;
    private Objective score;
    private int index = 15;
    private int titleTask;
    private int textTask;
    private List<String> title;
    private int titleIndex = 0;
    private List<String> text;
    private int updateTitle;
    private int updateScore;
    public void setTitle(List<String> title) {
        this.title = title;
    }
    public void setText(List<String> text) {
        this.text = text;
    }
    public void setUpdateTitle(int updateTitle) {
        this.updateTitle = updateTitle;
    }
    public PlayerBoard(TNTTag plugin, Player player, List<String> text, List<String> title, int updateTitle) {
        this.plugin = plugin;
        this.player = player;
        this.text = text;
        this.title = title;
        this.updateTitle = updateTitle;
        this.updateScore = updateScore;
        startSetup();
    }

    private void startSetup() {
        if (this.plugin.getBoards().containsKey(this.player)) {
            this.plugin.getBoards().get(this.player);
        }

        this.titleIndex = this.title.size();
        this.plugin.getBoards().put(this.player, this);

        buildScoreboard();
        updater();
    }

    public String setHolders(String s) {
        return Utils.CCS(PlaceholderAPI.setPlaceholders(this.player, s));
    }

    public String maxChars(int characters, String string) {
        if (ChatColor.translateAlternateColorCodes('&', string).length() > characters)
            return string.substring(0, characters);
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public void buildScoreboard() {
        this.board = Bukkit.getScoreboardManager().getNewScoreboard();
        if (this.score != null) this.score.unregister();
        this.score = this.board.registerNewObjective("score", "dummy");
        this.score.setDisplaySlot(DisplaySlot.SIDEBAR);
        if (this.title.size() == 0) {
            this.title.add(" ");
        }

        this.score.setDisplayName(setHolders(this.title.get(0)));
        for (String t : this.text) {
                if (this.index == 0){
                    return;
                }
                this.score.getScore(setHolders(t)).setScore(this.index);
                this.index--;
            }
        this.player.setScoreboard(this.board);
    }

    public void stopTasks() {
        /*  83 */     Bukkit.getScheduler().cancelTask(this.titleTask);
        /*     */   }

    public void updater(){
        this.titleTask = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, this::updateTitle, 0L, this.updateTitle);
    }

    public void updateTitle(){
        if (this.titleIndex > this.title.size() - 1) {
            this.titleIndex = 0;
        }

        this.score.setDisplayName(maxChars(32, PlaceholderAPI.setPlaceholders(this.player, this.title.get(this.titleIndex))));
        this.titleIndex++;
    }

    public void remove() {
        stopTasks();
        this.plugin.getBoards().remove(this.player);
        this.player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

}
