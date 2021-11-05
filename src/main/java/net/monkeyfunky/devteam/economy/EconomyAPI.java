package net.monkeyfunky.devteam.economy;

import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class EconomyAPI implements API {

    @Override
    public int get(UUID uuid) {
        int value = 0;
        try {
            Statement statement = Economy.getInstance().newStatement();

            ResultSet set = statement.executeQuery("SELECT * FROM money;");
            while (set.next()) {
                if (set.getString("uuid").equals(uuid.toString())) {
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
    public void set(UUID uuid, int value) {
        try {
            Statement statement = Economy.getInstance().newStatement();

            statement.executeUpdate("UPDATE money SET value = " + value + " WHERE uuid = '" + uuid + "';");

            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public boolean hasAccount(UUID uuid) {
        boolean contains = false;
        try {
            Statement statement = Economy.getInstance().newStatement();

            ResultSet set = statement.executeQuery("SELECT uuid FROM money;");
            while (set.next()) {
                if (set.getString("uuid").equals(uuid.toString())) {
                    contains = true;
                }
            }

            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return contains;
    }
}
