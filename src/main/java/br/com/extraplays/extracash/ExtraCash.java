package br.com.extraplays.extracash;

import br.com.extraplays.extracash.account.AccountManager;
import br.com.extraplays.extracash.commands.executor.CashCommand;
import br.com.extraplays.extracash.database.DatabaseManager;
import br.com.extraplays.extracash.keys.KeyManager;
import br.com.extraplays.extracash.listeners.EntityDeathListener;
import br.com.extraplays.extracash.listeners.PlayerJoinListener;
import br.com.extraplays.extracash.placeholder.HookPlaceholder;
import br.com.extraplays.extracash.tasks.SaveData;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class ExtraCash extends JavaPlugin {

    private DatabaseManager databaseManager;
    private AccountManager accountManager;

    @Getter  private KeyManager keyManager;
    @Getter
    private static ExtraCash instance;

    @Getter
    private File file, accountsFile;
    @Getter
    private FileConfiguration shopConfig, accountsConfig;


    @Override
    public void onEnable() {

        instance = this;
        accountManager = new AccountManager();
        keyManager = new KeyManager();

        file = new File(getDataFolder(), "shop.yml");
        if (!file.exists()) {
            try{
                file.createNewFile();
                saveResource("shop.yml", true);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        shopConfig = YamlConfiguration.loadConfiguration(file);

        saveDefaultConfig();
        setupDatabase();
        setupPlaceholders();
        setupListeners();
        setupCommand();


    }

    @Override
    public void onDisable() {

        if (getConfig().getBoolean("mysql.enable")){
            databaseManager.saveAccounts();
            databaseManager.close();
        }else {
            try{
                accountManager.saveAccounts();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

    }

    public void setupCommand(){
        getCommand("cash").setExecutor(new CashCommand());
    }

    public void setupListeners(){
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDeathListener(), this);
    }

    public void setupDatabase(){
        if (getConfig().getBoolean("mysql.enable")){

            ConfigurationSection section = getConfig().getConfigurationSection("mysql");

            databaseManager = new DatabaseManager(
                section.getString("host"),
                section.getString("user"),
                section.getString("password"),
                section.getString("database")
            );

            databaseManager.createTable();
            databaseManager.loadAccounts();

            new SaveData().runTaskTimerAsynchronously(this, 60L, 20L * 2400);

        }else {

            accountsFile = new File(getDataFolder(), "accounts.yml");
            if (!accountsFile.exists()){
                try {
                    accountsFile.createNewFile();
                }catch(IOException exception) {
                    exception.printStackTrace();
                }
            }
            accountsConfig = YamlConfiguration.loadConfiguration(accountsFile);


            accountManager.loadAccounts();

        }
    }

    public void setupPlaceholders(){
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            getLogger().info("Placeholder registrada.");
            new HookPlaceholder().register();
        }
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }
}
