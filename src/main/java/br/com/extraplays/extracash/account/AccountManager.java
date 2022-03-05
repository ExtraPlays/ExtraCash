package br.com.extraplays.extracash.account;

import br.com.extraplays.extracash.ExtraCash;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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

    public void loadAccounts(){

        FileConfiguration config = ExtraCash.getInstance().getAccountsConfig();
        ConfigurationSection section = config.getConfigurationSection("accounts");

        if (section != null) {
            for (String s : section.getKeys(false)) {
                accountMap.put(s, new Account(s, section.getInt(s)));
            }
        }

    }

    public  void saveAccounts() throws IOException {
        for (Map.Entry<String, Account> accounts : accountMap.entrySet()){

            ExtraCash.getInstance().getAccountsConfig().set("accounts." + accounts.getKey(), accounts.getValue().getBalance());
            ExtraCash.getInstance().getAccountsConfig().save(ExtraCash.getInstance().getAccountsFile());

        }
    }

}
