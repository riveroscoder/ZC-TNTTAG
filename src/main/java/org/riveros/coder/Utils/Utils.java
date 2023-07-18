package org.riveros.coder.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.Managers.Arena.Arena;
import org.riveros.coder.Managers.Arena.ArenaState;
import org.riveros.coder.Scoreboard.PlayerBoard;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    static FileConfigurationUtil yml = Utils.getScoreboard();

    private final static int CENTER_PX = 154;

    public static void giveItems(Player p, ArenaState s){
        if(s.equals(ArenaState.WAITING)){
            Inventory i = p.getInventory();

            i.clear();
            i.setItem(8, ItemBuilder.factItem1(355, 1, 0, "&c&lAbandonar", "&eClick para salir del mapa."));
        } else if(s.equals(ArenaState.FINISHING)){
            Inventory i = p.getInventory();
            i.clear();

            i.setItem(0, ItemBuilder.factItem1(339, 1, 0, "&6♨ &b&lPartida Aleatoria &6♨", "&eClick para entrar a una partida."));
            i.setItem(4, ItemBuilder.factItem1(46, 1, 0, "&6♨ &b&lCosmeticos &6♨", "&eClick para abrir los cosmeticos."));
            i.setItem(8, ItemBuilder.factItem1(138, 1, 0, "&6♨ &b&lLobbys &6♨", "&eClick para cambiar de menus."));
        }
    }

    public static FileConfigurationUtil getScoreboard(){
        File file = new File(TNTTag.getInstance().getDataFolder(), "scoreboard.yml");
        if (!file.exists()){
            TNTTag.getInstance().saveResource("scoreboard.yml", false);
        }
        return new FileConfigurationUtil(file);
    }


    public static void ScoreChange(Player p233, Arena a, ArenaState score){
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
            } else if(score.equals(ArenaState.FINISHING)){
                new PlayerBoard(TNTTag.getInstance(),
                        p233,
                        yml.getStringList("IN-GAME.LINES.text"),
                        yml.getStringList("TITLE.LINES"),
                        yml.getInt("TITLE.DELAY")).remove();
                new PlayerBoard(TNTTag.getInstance(),
                        p233,
                        yml.getStringList("IN-LOBBY.LINES.text"),
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

    public static String color(String text) {
        text = text.replaceAll("<1>", "✖");
        text = text.replaceAll("<2>", "✔");
        text = text.replaceAll("<3>", "●");
        text = text.replaceAll("<4>", "♥");
        text = text.replaceAll("<5>", "♦");
        text = text.replaceAll("<6>", "★");
        text = text.replaceAll("<7>", "►");
        text = text.replaceAll("<8>", "◄");
        text = text.replaceAll("<9>", "✪");
        text = text.replaceAll("<10>", "☠");
        text = text.replaceAll("<11>", "░");
        text = text.replaceAll("<12>", "■");
        text = text.replaceAll("<13>", "»");
        text = text.replaceAll("<14>", "«");
        text = text.replaceAll("<15>", "✘");
        text = text.replaceAll("<16>", "♣");
        text = text.replaceAll("<17>", "✐");
        text = text.replaceAll("<18>", "☯");
        text = text.replaceAll("<19>", "♛");
        text = text.replaceAll("<20>", "♞");
        text = text.replaceAll("<21>", "❆");
        text = text.replaceAll("<22>", "★");
        text = text.replaceAll("<23>", "✱");
        text = text.replaceAll("<24>", "✿");
        text = text.replaceAll("<25>", "◉");
        text = text.replaceAll("<26>", "❤");
        text = text.replaceAll("<27>", "㋡");
        text = text.replaceAll("<28>", "✚");
        text = text.replaceAll("<29>", "♨");
        text = text.replaceAll("<30>", "♥");
        text = text.replaceAll("<31>", "♬");
        text = text.replaceAll("<32>", "☃");
        text = text.replaceAll("<33>", "☢");
        text = text.replaceAll("<a>", "á");
        text = text.replaceAll("<e>", "é");
        text = text.replaceAll("<i>", "í");
        text = text.replaceAll("<o>", "ó");
        text = text.replaceAll("<u>", "ú");
        text = text.replaceAll("<u>", "ú");
        text = text.replaceAll("<A>", "Á");
        text = text.replaceAll("<E>", "É");
        text = text.replaceAll("<I>", "Í");
        text = text.replaceAll("<O>", "Ó");
        text = text.replaceAll("<U>", "Ú");
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static void consolemessage(String text) {
        Bukkit.getConsoleSender().sendMessage(color(text));
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
