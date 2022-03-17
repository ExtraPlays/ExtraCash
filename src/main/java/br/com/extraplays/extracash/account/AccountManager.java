package br.com.extraplays.extracash.account;

import br.com.extraplays.extracash.ExtraCash;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AccountManager {

    public HashMap<String, Account> accountMap = new HashMap<>();

    public Account getAccount(String ownerUUID){
        return accountMap.get(ownerUUID);
    }

    public int getFormatedBalance(String ownerUUID){
        return getAccount(ownerUUID).getBalance();
    }

    public boolean hasAccount(String ownerUUID){
        return accountMap.containsKey(ownerUUID);
    }

    public void createAccount(String uuid){

        Account account = new Account(uuid, 5);
        accountMap.put(uuid, account);

    }

    public void addBalance(String ownerUUID, int amount){
        getAccount(ownerUUID).balance += amount;
    }

    public void removeBalance(String ownerUUID, int amount){
        getAccount(ownerUUID).balance -= amount;
    }

    public void setBalance(String ownerUUID, int amount) {
        getAccount(ownerUUID).setBalance(amount);
    }

    public List<Account> getRanking() {
        return accountMap.values()
                .stream()
                .sorted(Comparator.comparingInt(Account::getBalance).reversed())
                .limit(10) // TESTE
                .collect(Collectors.toList());
    }

}
