package org.riveros.coder.Player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.riveros.coder.Database.Database;
import org.riveros.coder.Main.TNTTag;

import java.util.*;

public class Client {

    private UUID id;
    private String name;
    private TNTTag Main;

    private Account account;

    private Map<String, DataContainer> general;
    private Map<UUID, Long> lastHit = new HashMap<>();

    public Client(UUID id, String name) {
        this.id = id;
        this.name = name;

        this.account = new Account(id, name);
        this.general = Database.getInstance().loadStats(id, "lostedd_bedwars", name);

    }

//    public String makeProgressBar(boolean utf8) {
//        Level level = Level.getByLevel(this.getLevel());
//        double currentExp = this.getExp();
//        double needExp = level.getNext() == null ? 0.0 : level.getNext().getExp();
//        StringBuilder progressBar = new StringBuilder();
//        double percentage = currentExp >= needExp ? 100.0 : ((currentExp * 100.0) / needExp);
//
//        boolean higher = false, hasColor = false;
//        for (double d = (utf8 ? 10.0 : 2.5); d <= 100.0; d += (utf8 ? 10.0 : 2.5)) {
//            if (!higher && percentage >= d) {
//                progressBar.append((utf8 ? "§b" : "§3"));
//                higher = true;
//                hasColor = true;
//            } else if ((higher || !hasColor) && percentage < d) {
//                higher = false;
//                hasColor = true;
//                progressBar.append((utf8 ? "§7" : "§8"));
//            }
//
//            progressBar.append(percentage >= d ? (utf8 ? "■" : "|") : (utf8 ? "■" : "|"));
//        }
//
//        return progressBar.toString();
//    }

    public void setHit(UUID id) {
        this.lastHit.put(id, System.currentTimeMillis() + 8000);
    }

//    public void addExp(double exp) {
//        this.account.addExp(exp);
//    }

    public void refreshPlayer() {
        this.refreshPlayer(false);
    }

    public void refreshPlayer(boolean respawning) {
        this.refreshPlayer(true, respawning);
    }

    public void refreshPlayer(boolean teleport, boolean respawning) {
//        Player player = getPlayer();
//
//        player.setMaxHealth(20.0);
//        player.setHealth(20.0);
//        player.setFoodLevel(20);
//        player.setExhaustion(0.0f);
//        player.setExp(0.0f);
//        player.setLevel(0);
//        player.setAllowFlight(false);
//        player.closeInventory();
//        player.spigot().setCollidesWithEntities(true);
//        for (PotionEffect pe : player.getActivePotionEffects()) {
//            player.removePotionEffect(pe.getType());
//        }
//
//        player.getInventory().clear();
//        player.getInventory().setArmorContents(new ItemStack[4]);
//        if (inLobby()) {
//            player.setGameMode(GameMode.ADVENTURE);
//            player.setAllowFlight(player.hasPermission("zelitag.lobby.fly"));
//            int slot = Settings.lobby$hotbar$profile$slot;
//            if (slot < 9 && slot > -1) {
//                player.getInventory().setItem(slot,
//                        BukkitUtils.putProfileOnSkull(player, BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : display=" + language.lobby$hotbar$profile$name)));
//            }
//
//            slot = Settings.lobby$hotbar$shop$slot;
//            if (slot < 9 && slot > -1) {
//                player.getInventory().setItem(slot, BukkitUtils.deserializeItemStack("EMERALD : 1 : display=" + language.lobby$hotbar$shop$name));
//            }
//
//            slot = Settings.lobby$hotbar$players$slot;
//            if (slot < 9 && slot > -1) {
//                player.getInventory().setItem(slot, BukkitUtils.deserializeItemStack(
//                        "INK_SACK:" + (canSeePlayers() ? "10" : "8") + " : 1 : display=" + (canSeePlayers() ? language.lobby$hotbar$players$name_v : language.lobby$hotbar$players$name_i)));
//            }
//
//            Rank.getRank(player).apply(player);
//            if (teleport) {
//                player.teleport(SetLobbyCommand.getSpawnLocation());
//            }
//            if (Settings.lobby$speed$enabled) {
//                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, Settings.lobby$speed$level - 1));
//            }
//            if (Settings.lobby$jump_boost$enabled) {
//                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, Settings.lobby$jump_boost$level - 1));
//            }
//        } else if (server.getState().canJoin()) {
//            player.setGameMode(GameMode.ADVENTURE);
//
//            int slot = Settings.game$hotbar$quit$slot;
//            if (slot < 9 && slot > -1) {
//                player.getInventory().setItem(slot, BukkitUtils.deserializeItemStack("BED : 1 : display=" + language.game$hotbar$quit$name));
//            }
//        } else if (server.isSpectator(player)) {
//            player.setGameMode(GameMode.ADVENTURE);
//            player.setAllowFlight(true);
//            player.setFlying(true);
//            player.spigot().setCollidesWithEntities(false);
//
//            if (!respawning) {
//                int slot = Settings.game$hotbar$compass$slot;
//                if (slot < 9 && slot > -1) {
//                    player.getInventory().setItem(slot, BukkitUtils.deserializeItemStack("COMPASS : 1 : display=" + language.game$hotbar$compass$name));
//                }
//
//                slot = Settings.game$hotbar$play_again$slot;
//                if (slot < 9 && slot > -1) {
//                    player.getInventory().setItem(slot, BukkitUtils.deserializeItemStack("PAPER : 1 : display=" + language.game$hotbar$play_again$name));
//                }
//
//                slot = Settings.game$hotbar$quit_spectator$slot;
//                if (slot < 9 && slot > -1) {
//                    player.getInventory().setItem(slot, BukkitUtils.deserializeItemStack("BED : 1 : display=" + language.game$hotbar$quit_spectator$name));
//                }
//            } else {
//                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
//            }
//        } else {
//            player.setGameMode(GameMode.SURVIVAL);
//        }
//
//        player.updateInventory();
    }

    public void reloadScoreboard() {
//        this.scoreboard = new LostScoreboard() {
//            @Override
//            public void update() {
//                this.updateHealth();
//                List<String> clone = new ArrayList<>(
//                        server == null ? language.scoreboards$lines$lobby : server.getState().canJoin() ? language.scoreboards$lines$waiting : language.scoreboards$lines$ingame);
//                Collections.reverse(clone);
//                for (int i = 0; i < clone.size(); i++) {
//                    String line = clone.get(i);
//                    if (Main.placeholderapi) {
//                        line = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(getPlayer(), line);
//                    }
//
//                    if (server == null) {
//                        long totalkills = 0;
//                        long totalWins = 0;
//                        for (String mode : new String[] {"solo", "doubles", "trio", "squad"}) {
//                            totalkills += getLong(mode, "kills");
//                            totalWins += getLong(mode, "wins");
//                        }
//
//                        Level level = Level.getByLevel(getLevel());
//                        line = line.replace("{level}", StringUtils.formatNumber(level.getLevel()));
//                        line = line.replace("{exp}", StringUtils.formatPerMil(getExp()));
//                        line = line.replace("{nextExp}", level.getNext() != null ? StringUtils.formatPerMil(level.getNext().getExp()) : "Max");
//                        line = line.replace("{progressbar}", makeProgressBar(true));
//                        line = line.replace("{coins}", StringUtils.formatNumber(getCoins()));
//                        line = line.replace("{totalkills}", StringUtils.formatNumber(totalkills));
//                        line = line.replace("{totalwins}", StringUtils.formatNumber(totalWins));
//                    } else {
//                        line = line.replace("{date}", new SimpleDateFormat("MM/dd/yy").format(System.currentTimeMillis()));
//                        line = line.replace("{world}", server.getServerName());
//                        line = line.replace("{event}", ((String) language.reflect(server.getEvent())).replace("{tier}", server.getEventTier()));
//                        line = line.replace("{time}", server.getEventTime());
//                        line = line.replace("{mode}", server.getMode().getName(language));
//                        line = line.replace("{map}", server.getName());
//                        line = line.replace("{on}", String.valueOf(server.getAlive()));
//                        line = line.replace("{teams}", String.valueOf(server.getAliveTeams().size()));
//                        line = line.replace("{max}", String.valueOf(server.getMaxPlayers()));
//
//                        if (line.equals("removethatline")) {
//                            continue;
//                        }
//                    }
//
//                    this.add(i + 1, line);
//                }
//            }
//        }.to(this.getPlayer()).scroller(new ScoreboardScroller(Settings.scoreboards$animation$title)).build();
//        this.scoreboard.update();
//        if (!this.inLobby()) {
//            this.scoreboard.health();
//            if (this.server.getState() == BedWarsState.INGAME) {
//                this.scoreboard.healthTab();
//            }
//        }
//        this.scoreboard.scroll();
    }

//    public void add(String field, String key) {
//        this.add(field, key, 1);
//    }

    public void setCoins(int amount) {
        if (Main.getEconomy() != null) {
            ((net.milkbowl.vault.economy.Economy) Main.getEconomy()).withdrawPlayer(Bukkit.getOfflinePlayer(this.getUniqueId()),
                    ((net.milkbowl.vault.economy.Economy) Main.getEconomy()).getBalance(Bukkit.getOfflinePlayer(this.getUniqueId())));
            ((net.milkbowl.vault.economy.Economy) Main.getEconomy()).depositPlayer(Bukkit.getOfflinePlayer(this.getUniqueId()), amount);
            return;
        }

        general.get("coins").set(amount);
    }

    public void addCoins(int amount) {
        if (Main.getEconomy() != null) {
            ((net.milkbowl.vault.economy.Economy) Main.getEconomy()).depositPlayer(Bukkit.getOfflinePlayer(this.getUniqueId()), amount);
            return;
        }

        general.get("coins").addInt(amount);
    }

    public void removeCoins(int amount) {
        if (Main.getEconomy() != null) {
            ((net.milkbowl.vault.economy.Economy) Main.getEconomy()).withdrawPlayer(Bukkit.getOfflinePlayer(this.getUniqueId()), amount);
            return;
        }

        general.get("coins").removeInt(amount);
    }

    public int getCoins() {
        if (Main.getEconomy() != null) {
            return (int) ((net.milkbowl.vault.economy.Economy) Main.getEconomy()).getBalance(Bukkit.getOfflinePlayer(this.getUniqueId()));
        }

        return general.get("coins").getAsInt();
    }

    public String getPrefix(String field) {
        if (field.equals("solo")) {
            return "1v1";
        } else if (field.equals("doubles")) {
            return "2v2";
        } else if (field.equals("trio")) {
            return "3v3";
        } else if (field.equals("squad")) {
            return "4v4";
        }

        return "";
    }


    public int getLevel() {
        return account.getLevel();
    }

    public double getExp() {
        return account.getExp();
    }

    public void save() {
        this.account.save();
        Database.getInstance().saveStats(this.id, "lostedd_bedwars", this.general);
    }

    public void destroy() {
        this.id = null;
        this.name = null;
        this.account = null;
        this.general.clear();
        this.general = null;
    }

    public String getName() {
        return name;
    }

    public UUID getUniqueId() {
        return id;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(id);
    }
}

