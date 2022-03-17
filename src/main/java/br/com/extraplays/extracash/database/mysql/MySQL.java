package br.com.extraplays.extracash.database.mysql;

import br.com.extraplays.extracash.database.Storage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class MySQL implements Storage {

    @Setter @Getter
    private String host, user, pass, database;

    public Connection connection;

    public MySQL(String host, String user, String pass, String database) {
        setHost(host);
        setUser(user);
        setPass(pass);
        setDatabase(database);
        connect();
        createTable();
    }

    @Override
    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            long timeout = System.currentTimeMillis();
            this.connection = DriverManager.getConnection("jdbc:mysql://" + getHost() + ":3306/" + getDatabase(), getUser(), getPass());
            System.out.println("Conectado com o banco de dados MYSQL - " + (System.currentTimeMillis() - timeout ) + " MS" );
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        if (getConnection() != null) {
            try {
                System.out.println("Desconectado do banco de dados");
                getConnection().close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
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
                    "id int(11) NOT NULL AUTO_INCREMENT, " +
                    "uuid char(36), " +
                    "balance int DEFAULT 0 NOT NULL," +
                    "PRIMARY KEY (id)" +
                    ") "
            );

            Bukkit.getLogger().info("[ExtraCash] Tabela accounts carregada.");

        }catch (SQLException e){
            Bukkit.getLogger().info("[ExtraCash] Erro ao carregar a tabela accounts.");
        }

        try (Statement statement = getConnection().createStatement()){


            statement.execute("CREATE TABLE IF NOT EXISTS `keys` (" +
                    "`id` int (11) NOT NULL AUTO_INCREMENT," +
                    "`key` varchar(100)," +
                    "`value` int," +
                    "`created_at` timestamp DEFAULT CURRENT_TIMESTAMP," +
                    "`used` boolean DEFAULT false," +
                    "PRIMARY KEY (id)" +
                    ")"
            );

            Bukkit.getLogger().info("[ExtraCash] Tabela keys carregada.");

        }catch (SQLException e){
            Bukkit.getLogger().info("[ExtraCash] Erro ao carregar a tabela keys. \n" + e.getMessage());
        }
    }

}
