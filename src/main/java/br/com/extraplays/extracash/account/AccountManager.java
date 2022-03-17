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
    public HashMap<String, Account> topMap = new LinkedHashMap<>();

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

    public Map<String, Integer> getTop(int startin, int stopin) {
        Map<String, Integer> top = new LinkedHashMap<>(stopin);
        ArrayList<Account> players = new ArrayList<>();
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            Account account = getAccount(player.getUniqueId().toString());
            if (account.getBalance() == 0) continue;

            players.add(account);
        }

        players.sort(Account::compareTo);

        System.out.println(players.size());

        for (int i = startin; i < stopin; i++) {
            try {
                Account account = players.get(i);
                top.put(Bukkit.getPlayer(account.getUuid()).getName(), account.getBalance());
            } catch (Exception e) {
                break;
            }
        }
        return top;
    }

    public List<Account> getRanking() {
        return accountMap.values()
                .stream()
                .sorted(Comparator.comparingDouble(account -> account.balance))
                .collect(Collectors.toList());
    }

}
