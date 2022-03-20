package br.com.extraplays.extracash.database.sqlite;

import br.com.extraplays.extracash.ExtraCash;
import br.com.extraplays.extracash.database.Storage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLite implements Storage {

    private File file;
    private Connection connection;

    public SQLite(){

        this.file = new File(ExtraCash.getInstance().getDataFolder(), "database.db");
        try{
            file.createNewFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        connect();
        createTable();

    }

    @Override
    public void connect() {
        String url = "jdbc:sqlite:" + file;

        try{
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(url);
            Bukkit.getConsoleSender().sendMessage("[ExtraCash] " + ChatColor.GREEN + "Conectado com SQLite.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        if (getConnection() != null) {
            try {
                getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public void createTable() {

        try (Statement statement = getConnection().createStatement()){

            statement.execute("CREATE TABLE IF NOT EXISTS accounts (" +
                    "uuid VARCHAR(36) PRIMARY KEY, " +
                    "balance INTEGER DEFAULT 0 NOT NULL" +
                    ") "
            );

            Bukkit.getLogger().info("[ExtraCash] Tabela accounts carregada.");

        }catch (SQLException e){
            Bukkit.getLogger().info("[ExtraCash] Erro ao carregar a tabela accounts.");
        }

        try (Statement statement = getConnection().createStatement()){


            statement.execute("CREATE TABLE IF NOT EXISTS `keys` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "`key` TEXT," +
                    "`value` INTEGER," +
                    "`created_at` TEXT," +
                    "`used` BOOLEAN DEFAULT false" +
                    ")"
            );

            Bukkit.getLogger().info("[ExtraCash] Tabela keys carregada.");

        }catch (SQLException e){
            Bukkit.getLogger().info("[ExtraCash] Erro ao carregar a tabela keys. \n" + e.getMessage());
        }

    }

}
