package net.monkeyfunky.devteam.economy;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class VaultEconomy implements Economy {
    private API api;

    public VaultEconomy(API api) {
        this.api = api;
    }

    public void setAPI(API api) {
        this.api = api;
    }

    public UUID getUUID(String name) {
        return Objects.requireNonNull(Bukkit.getPlayer(name)).getUniqueId();
    }

    public UUID getUUID(OfflinePlayer player) {
        return Objects.requireNonNull(player).getUniqueId();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "ZomZone";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double amount) {
        return null;
    }

    @Override
    public String currencyNamePlural() {
        return null;
    }

    @Override
    public String currencyNameSingular() {
        return null;
    }

    @Override
    public boolean hasAccount(String playerName) {
        return api.hasAccount(getUUID(playerName));
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        return api.hasAccount(getUUID(player));
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return hasAccount(playerName);
    }

    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return hasAccount(player);
    }

    @Override
    public double getBalance(String playerName) {
        return api.get(getUUID(playerName));
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        return api.get(getUUID(player));
    }

    @Override
    public double getBalance(String playerName, String world) {
        return getBalance(playerName);
    }

    @Override
    public double getBalance(OfflinePlayer player, String world) {
        return getBalance(player);
    }

    @Override
    public boolean has(String playerName, double amount) {
        return amount < getBalance(playerName);
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        return amount < getBalance(player);
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return amount < getBalance(playerName);
    }

    @Override
    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return amount < getBalance(player);
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds");
        }

        if (!has(playerName, amount)) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player don't have enough money");

        api.set(getUUID(playerName), (int) (api.get(getUUID(playerName)) - amount));
        return new EconomyResponse(0, api.get(getUUID(playerName)), EconomyResponse.ResponseType.SUCCESS, "OK");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds");
        }

        if (!has(player, amount)) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player don't have enough money");

        api.set(getUUID(player), (int) (api.get(getUUID(player)) - amount));
        return new EconomyResponse(0, api.get(getUUID(player)), EconomyResponse.ResponseType.SUCCESS, "OK");
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return withdrawPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        return withdrawPlayer(player, amount);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds");
        }

        api.set(getUUID(playerName), (int) (api.get(getUUID(playerName)) + amount));
        return new EconomyResponse(0, api.get(getUUID(playerName)), EconomyResponse.ResponseType.SUCCESS, "OK");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds");
        }

        api.set(getUUID(player), (int) (api.get(getUUID(player)) + amount));
        return new EconomyResponse(0, api.get(getUUID(player)), EconomyResponse.ResponseType.SUCCESS, "OK");
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return depositPlayer(player, amount);
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Banks are not supported");
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Banks are not supported");
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Banks are not supported");
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Banks are not supported");
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Banks are not supported");
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Banks are not supported");
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Banks are not supported");
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Banks are not supported");
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Banks are not supported");
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Banks are not supported");
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Banks are not supported");
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        return true;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        return true;
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return true;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return true;
    }
}
