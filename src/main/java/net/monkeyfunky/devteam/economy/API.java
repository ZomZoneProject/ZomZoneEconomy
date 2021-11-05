package net.monkeyfunky.devteam.economy;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface API {
    int get(UUID uuid);
    void set(UUID uuid, int value);
    boolean hasAccount(UUID uuid);
}
