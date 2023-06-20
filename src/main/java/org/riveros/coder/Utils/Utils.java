package org.riveros.coder.Utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.riveros.coder.FileConfig.ScoreB;
import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.Managers.Arena.Arena;
import org.riveros.coder.Managers.Arena.ArenaState;
import org.riveros.coder.Scoreboard.PlayerBoard;

public class Utils {

    FileConfiguration yml = ScoreB.getScoreboard();

    private final static int CENTER_PX = 154;

    public void ScoreChange(Player p233, Arena a, ArenaState score){
        if (yml.getBoolean("ENABLED") && yml.getStringList("WORLDS").contains(p233.getPlayer().getWorld().getName())) {
            if(score.equals(ArenaState.WAITING)){
                new PlayerBoard(TNTTag.getInstance(),
                        p233,
                        yml.getStringList("IN-LOBBY.LINES.text"),
                        yml.getStringList("TITLE.LINES"),
                        yml.getInt("TITLE.DELAY")).remove();
                new PlayerBoard(TNTTag.getInstance(),
                        p233,
                        yml.getStringList("WAITING.LINES.text"),
                        yml.getStringList("TITLE.LINES"),
                        yml.getInt("TITLE.DELAY")
                );
            } else if(score.equals(ArenaState.IN_GAME)){
                new PlayerBoard(TNTTag.getInstance(),
                        p233,
                        yml.getStringList("WAITING.LINES.text"),
                        yml.getStringList("TITLE.LINES"),
                        yml.getInt("TITLE.DELAY")).remove();
                new PlayerBoard(TNTTag.getInstance(),
                        p233,
                        yml.getStringList("IN-GAME.LINES.text"),
                        yml.getStringList("TITLE.LINES"),
                        yml.getInt("TITLE.DELAY")
                );
            }
        }
    }

    public static String CCS(String msg){
        return ChatColor.translateAlternateColorCodes('&', (msg));
    }

    public static void CC(Player p, String s){
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', (s)));
    }

    public static void CCT(Player p, String s, String s2){ Reflections.send(p, 5, 2, 5, ChatColor.translateAlternateColorCodes('&', (s)), ChatColor.translateAlternateColorCodes('&', (s2))); }

    public static void CCC(Player player, String message){
        if(message == null || message.equals("")) player.sendMessage("");
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray()){
            if(c == '§'){
                previousCode = true;
                continue;
            }else if(previousCode == true){
                previousCode = false;
                if(c == 'l' || c == 'L'){
                    isBold = true;
                    continue;
                }else isBold = false;
            }else{
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate){
            sb.append(" ");
            compensated += spaceLength;
        }
        player.sendMessage(sb.toString() + message);
    }

}
