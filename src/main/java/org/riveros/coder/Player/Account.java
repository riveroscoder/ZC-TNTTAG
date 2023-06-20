package org.riveros.coder.Player;

import org.json.simple.JSONArray;
import org.riveros.coder.Database.Database;

import java.util.Map;
import java.util.UUID;

public class Account {

    private UUID id;
    private String name;

    // lastRank, language, level, exp, collectibles, deliveries, leveling, players, gore
    private Map<String, DataContainer> data;

    public Account(UUID id, String name) {
        this.id = id;
        this.name = name;

        this.data = Database.getInstance().loadStats(id, "zelitag_account", name);
    }

    public void save() {
        Database.getInstance().saveStats(this.id, "zelitag_account", this.data);
    }

    public void setLanguage(String language) {
        this.getData("language").set(language);
    }

    public void setCanSeePlayers(boolean flag) {
        this.getData("players").set(flag);
    }

    public void setCanSeeBlood(boolean flag) {
        this.getData("gore").set(flag);
    }

    public boolean isLeveled(int level) {
        return this.getData("leveling").getAsJsonArray().contains(String.valueOf(level));
    }

    public UUID getUniqueId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return this.getData("language").getAsString();
    }

    public int getLevel() {
        return this.getData("level").getAsInt();
    }

    public double getExp() {
        return this.getData("exp").getAsDouble();
    }

    public double getCoins() {
        return this.getData("coins").getAsDouble();
    }

    public boolean canSeePlayers() {
        return this.getData("players").getAsBoolean();
    }

    public boolean canSeeBlood() {
        return this.getData("gore").getAsBoolean();
    }

    public DataContainer getData(String dataName) {
        return data.get(dataName);
    }
}
