package org.riveros.coder.Database;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.Player.Client;
import org.riveros.coder.Player.DataContainer;

import javax.sql.rowset.CachedRowSet;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public abstract class Database {

    public abstract Map<String, DataContainer> loadStats(UUID id, String table, String name);

    public abstract void saveStats(UUID id, String table, Map<String, DataContainer> map);

    public abstract Client loadAccount(UUID id, String name);

    public abstract Client loadOffline(String name);

    public abstract Client unloadAccount(UUID id);

    public abstract Client getAccount(UUID id);

    public abstract Collection<Client> listAccounts();

    public abstract void update(String sql, Object... vars);

    public abstract void execute(String sql, Object... vars);

    public abstract CachedRowSet query(String query, Object... vars);
    

    public static void setupDatabase() {
        FileConfiguration config = TNTTag.getInstance().getConfig();
        String type = config.getString("database.name");
        MySQLDatabase instance2 = new MySQLDatabase();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(TNTTag.getInstance(), () -> {
            //instance2.listAccounts().stream().filter(client -> client.inLobby()).forEach(client -> client.getScoreboard().update());
        }, 0, 20);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(TNTTag.getInstance(), () -> {

        }, 0, 5);
    }

    public static Database getInstance() {
        return new MySQLDatabase();
    }
}
