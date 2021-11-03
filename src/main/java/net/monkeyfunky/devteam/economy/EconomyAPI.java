package net.monkeyfunky.devteam.economy;

import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EconomyAPI implements API {

    @Override
    public int get(Player player) {
        int value = 0;
        try {
            Statement statement = Economy.getInstance().newStatement();

            ResultSet set = statement.executeQuery("SELECT * FROM money;");
            while (set.next()) {
                if (set.getString("uuid").equals(player.getUniqueId().toString())) {
                    value = set.getInt("value");
                }
            }

            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return value;
    }

    @Override
    public void set(Player player, int value) {
        try {
            Statement statement = Economy.getInstance().newStatement();

            statement.executeUpdate("UPDATE money SET value = " + value + " WHERE uuid = '" + player.getUniqueId() + "';");

            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
