package net.monkeyfunky.devteam.economy;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
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

    private VaultEconomy economy;

    @Override
    public void onEnable() {
        instance = this;
        api = new EconomyAPI();
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);

        try {
            Class.forName("org.sqlite.JDBC");

            connection = DriverManager.getConnection("jdbc:sqlite:" + new File(getDataFolder().getParentFile(), "economy.db").getAbsolutePath());

            Statement statement = connection.createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS money(uuid text, value integer);");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        if (economy == null) {
            Plugin vault = getServer().getPluginManager().getPlugin("Vault");
            if (vault != null) {
                if (vault.isEnabled()) {
                    if (economy != null) {
                        return;
                    }

                    economy = new VaultEconomy(api);
                    getServer().getServicesManager().register(net.milkbowl.vault.economy.Economy.class, economy, this, ServicePriority.Normal);
                } else {
                    getServer().getPluginManager().registerEvents(new Listener() {
                        @EventHandler(ignoreCancelled = true)
                        public void onPluginEnable(PluginEnableEvent e) {
                            if (!e.getPlugin().getName().equals("Vault")) {
                                return;
                            }
                            getServer().getServicesManager().register(net.milkbowl.vault.economy.Economy.class, economy, Economy.getInstance(), ServicePriority.Normal);
                            PluginEnableEvent.getHandlerList().unregister(this);
                        }
                    }, this);
                }
            }
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
