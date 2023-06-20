package org.riveros.coder.Database;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.Player.Client;
import org.riveros.coder.Player.DataContainer;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class MySQLDatabase extends Database {

    private String host;
    private String port;
    private String dbname;
    private String username;
    private String password;

    private Connection connection;
    private ExecutorService executor;

    public MySQLDatabase() {
        FileConfiguration config = TNTTag.getInstance().getConfig();
        this.host = config.getString("database.host");
        this.port = config.get("database.port").toString();
        this.dbname = config.getString("database.dbname");
        this.username = config.getString("database.username");
        this.password = config.getString("database.password");

        this.openConnection();
        this.executor = Executors.newCachedThreadPool();
        this.createAccountTable();
        this.createBedWarsTable();
    }

    public boolean needsConversion() {
        try {
            return query("SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = '" + this.dbname + "' AND table_name = 'zelitag_tnttag';").getInt(1) > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    private void createAccountTable() {
        this.update("CREATE TABLE IF NOT EXISTS zelitag_account (id VARCHAR(36) NOT NULL, name VARCHAR (32) NOT NULL, lastRank VARCHAR(2) NOT NULL,"
                + "coins INTEGER, exp DOUBLE,"
                + "PRIMARY KEY(id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;");
    }

    private void createBedWarsTable() {
        this.update(
                "CREATE TABLE IF NOT EXISTS zelitag_tnttag (id VARCHAR(36) NOT NULL, name VARCHAR(32) NOT NULL, 1v1tagged LONG, 1v1tags LONG, 1v1wins LONG, 1v1defeats LONG, coins INTEGER, PRIMARY KEY(id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;");
    }

    @Override
    public Map<String, DataContainer> loadStats(UUID id, String table, String name) {
        Map<String, DataContainer> map = new LinkedHashMap<>();
        CachedRowSet rs = this.query("SELECT * FROM `" + table + "` WHERE `id` = ?", id.toString());
        if (rs != null) {
            try {
                for (int collumn = 2; collumn <= rs.getMetaData().getColumnCount(); collumn++) {
                    String key = rs.getMetaData().getColumnName(collumn);
                    if (key.equals("name")) {
                        String oldName = rs.getString(key);
                        if (!oldName.equals(name)) {
                            this.execute("UPDATE `" + table + "` SET `name` = ? WHERE `id` = ?", name, id.toString());
                        }

                        continue;
                    }

                    map.put(key, new DataContainer(rs.getObject(key)));
                }
            } catch (SQLException ex) {
                Bukkit.getServer().getConsoleSender().sendMessage("Could not loadStats(\"" + name + "\"): " + ex);

            }

            return map;
        }

        if (table.equals("zelitag_account")) {
            map.put("level", new DataContainer(1));
            map.put("exp", new DataContainer(0.0));
            map.put("leveling", new DataContainer("[]"));
            map.put("players", new DataContainer(true));
            map.put("gore", new DataContainer(true));
        } else if (table.equals("zelitag_tnttag")) {
            for (String statsPrefix : new String[] {"1v1", "2v2", "3v3", "4v4"}) {
                map.put(statsPrefix + "tagged", new DataContainer(0L));
                map.put(statsPrefix + "tags", new DataContainer(0L));
                map.put(statsPrefix + "wins", new DataContainer(0L));
                map.put(statsPrefix + "defeats", new DataContainer(0L));
                map.put(statsPrefix + "deaths", new DataContainer(0L));
            }
            map.put("coins", new DataContainer(0));
        }

        List<Object> list = new ArrayList<>();
        list.add(id.toString());
        list.add(name);
        list.addAll(map.values().stream().map(sc -> sc.get()).collect(Collectors.toList()));
        this.execute("INSERT INTO `" + table + "` VALUES (?, ?, " + StringUtils.repeat("?, ", map.size() - 1) + "?)", list.toArray(new Object[list.size()]));
        list.clear();
        list = null;
        return map;
    }

    @Override
    public void saveStats(UUID id, String table, Map<String, DataContainer> map) {
        StringBuilder sb = new StringBuilder("UPDATE `" + table + "` SET ");
        List<String> keys = new ArrayList<>(map.keySet());
        for (int slot = 0; slot < keys.size(); slot++) {
            String key = keys.get(slot);
            sb.append("`" + key + "` = ?");
            if (slot + 1 == keys.size()) {
                continue;
            }

            sb.append(", ");
        }

        sb.append(" WHERE `id` = ?");

        List<Object> values = new ArrayList<>();
        values.addAll(map.values().stream().map(sc -> sc.get()).collect(Collectors.toList()));
        values.add(id.toString());
        this.execute(sb.toString(), values.toArray(new Object[values.size()]));

        keys.clear();
        values.clear();
        keys = null;
        values = null;
    }

    private Map<UUID, Client> accounts = new HashMap<>();

    @Override
    public Client loadAccount(UUID id, String name) {
        Client account = accounts.get(id);
        if (account == null) {
            account = new Client(id, name);
            this.accounts.put(id, account);
        }

        return account;
    }

    @Override
    public Client loadOffline(String name) {
        CachedRowSet rs = query("SELECT * FROM `zelitag_account` WHERE LOWER(`name`) = ?", name.toLowerCase());
        if (rs == null) {
            return null;
        }

        try {
            return new Client(UUID.fromString(rs.getString("id")), name);
        } catch (SQLException ex) {
            Bukkit.getServer().getConsoleSender().sendMessage("loadOffline(\"" + name + "\"): " + ex);
            return null;
        }
    }

    @Override
    public Client unloadAccount(UUID id) {
        return accounts.remove(id);
    }

    @Override
    public Client getAccount(UUID id) {
        return accounts.get(id);
    }

    @Override
    public Collection<Client> listAccounts() {
        return ImmutableList.copyOf(accounts.values());
    }

    public void openConnection() {
        try {
            boolean reconnected = true;
            if (this.connection == null) {
                reconnected = false;
            }
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + port + "/" + dbname + "?verifyServerCertificate=false&useSSL=false&useUnicode=yes&characterEncoding=UTF-8", username, password);
            if (reconnected) {
                Bukkit.getServer().getConsoleSender().sendMessage("Reconectando to MySQL");
                return;
            }

            Bukkit.getServer().getConsoleSender().sendMessage("Connected to MySQL");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void closeConnection() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        if (!isConnected()) {
            this.openConnection();
        }

        return connection;
    }

    public boolean isConnected() {
        try {
            return !(connection == null || connection.isClosed() || !connection.isValid(5));
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void update(String sql, Object... vars) {
        try {
            PreparedStatement ps = prepareStatement(sql, vars);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void execute(String sql, Object... vars) {
        executor.execute(() -> {
            update(sql, vars);
        });
    }

    public PreparedStatement prepareStatement(String query, Object... vars) {
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            for (int i = 0; i < vars.length; i++) {
                ps.setObject(i + 1, vars[i]);
            }
            return ps;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public CachedRowSet query(String query, Object... vars) {
        CachedRowSet rowSet = null;
        try {
            Future<CachedRowSet> future = executor.submit(new Callable<CachedRowSet>() {

                @Override
                public CachedRowSet call() {
                    try {
                        PreparedStatement ps = prepareStatement(query, vars);

                        ResultSet rs = ps.executeQuery();
                        CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
                        crs.populate(rs);
                        rs.close();
                        ps.close();

                        if (crs.next()) {
                            return crs;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return null;
                }
            });

            if (future.get() != null) {
                rowSet = future.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rowSet;
    }
}
