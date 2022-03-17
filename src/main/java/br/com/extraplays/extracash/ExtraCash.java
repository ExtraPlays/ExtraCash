package br.com.extraplays.extracash;

import br.com.extraplays.extracash.account.AccountManager;
import br.com.extraplays.extracash.commands.executor.CashCommand;
import br.com.extraplays.extracash.config.ExtraConfig;
import br.com.extraplays.extracash.database.DatabaseManager;
import br.com.extraplays.extracash.database.Storage;
import br.com.extraplays.extracash.database.StorageType;
import br.com.extraplays.extracash.database.mysql.MySQL;
import br.com.extraplays.extracash.database.sqlite.SQLite;
import br.com.extraplays.extracash.keys.KeyManager;
import br.com.extraplays.extracash.listeners.EntityDeathListener;
import br.com.extraplays.extracash.listeners.PlayerJoinListener;
import br.com.extraplays.extracash.placeholder.HookPlaceholder;
import br.com.extraplays.extracash.tasks.SaveData;
import br.com.extraplays.extracash.utils.MessageUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class ExtraCash extends JavaPlugin {


    private StorageType storageType;
    private Storage storage;

    private DatabaseManager databaseManager;
    private AccountManager accountManager;

    private KeyManager keyManager;

    @Getter private static ExtraCash instance;

    public ExtraConfig messages, shop;

    @Override
    public void onEnable() {

        instance = this;
        accountManager = new AccountManager();
        keyManager = new KeyManager();

        messages = new ExtraConfig("messages");
        shop = new ExtraConfig("shop");
        MessageUtil.LoadMessages(messages.config());

        saveDefaultConfig();
        setupListeners();
        setupCommand();
        setupPlaceholders();
        setupDatabase();
        databaseManager = new DatabaseManager(storage);
        databaseManager.loadAccounts();
        databaseManager.loadKeys();


    }

    @Override
    public void onDisable() {

        databaseManager.saveAccounts();
        databaseManager.saveKeys();
        storage.disconnect();

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

            storageType = StorageType.MYSQL;
            storage = new MySQL(
                    section.getString("host"),
                    section.getString("user"),
                    section.getString("password"),
                    section.getString("database")
            );

        }else {
            storageType = StorageType.SQLITE;
            storage = new SQLite();
        }

        new SaveData().runTaskTimerAsynchronously(this, 60L, 20L * 1200);

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
