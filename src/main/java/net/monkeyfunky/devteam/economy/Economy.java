package net.monkeyfunky.devteam.economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class Economy extends JavaPlugin {
    private static Economy instance;

    private static EconomyAPI api;
    private Connection connection;

    @Override
    public void onEnable() {
        instance = this;
        api = new EconomyAPI();
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);

        try {
            Class.forName("org.sqlite.JDBC");

            connection = DriverManager.getConnection("jdbc:sqlite:" + new File(getDataFolder(), "npc.db").getAbsolutePath());

            Statement statement = connection.createStatement();

            statement.executeQuery("CREATE TABLE money(uuid text, value integer);");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Economy getInstance() {
        return instance;
    }

    public API getAPI() {
        return api;
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement newStatement() throws SQLException {
        return connection.createStatement();
    }
}
