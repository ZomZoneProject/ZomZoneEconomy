package net.monkeyfunky.devteam.economy;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        try {
            Statement statement = Economy.getInstance().newStatement();

            ResultSet set = statement.executeQuery("SELECT uuid FROM money;");
            boolean contains = false;
            while (set.next()) {
                if (set.getString("uuid").equals(e.getPlayer().getUniqueId().toString())) {
                    contains = true;
                }
            }

            if (!contains) {
                statement.executeQuery("INSERT INTO money VALUES('" + e.getPlayer().getUniqueId() + "', 0);");
            }

            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
