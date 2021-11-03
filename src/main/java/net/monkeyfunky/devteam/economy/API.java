package net.monkeyfunky.devteam.economy;

import org.bukkit.entity.Player;

public interface API {
    int get(Player player);
    void set(Player player, int value);
}
