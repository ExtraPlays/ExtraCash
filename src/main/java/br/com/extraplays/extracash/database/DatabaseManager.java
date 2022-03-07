package br.com.extraplays.extracash.database;

import br.com.extraplays.extracash.account.Account;
import br.com.extraplays.extracash.account.AccountManager;
import br.com.extraplays.extracash.ExtraCash;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.Map;

public class DatabaseManager {

    @Getter @Setter
    String host, user, pass, database;
    @Getter @Setter
    Connection connection;

    public DatabaseManager(String host,String user,String pass,String database){

        setHost(host);
        setUser(user);
        setPass(pass);
        setDatabase(database);

        try {
//            Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            long timeout = System.currentTimeMillis();

            setConnection(DriverManager.getConnection("jdbc:mysql://" + getHost() + ":3306/" + getDatabase(), getUser(), getPass()));

            long endTime = (System.currentTimeMillis() - timeout);

            Bukkit.getLogger().info("[ExtraCash] Conectado com ao banco de dados. " + endTime + "Ms");

        } catch (ClassNotFoundException | SQLException e) {
            Bukkit.getLogger().info("[ExtraCash] Não foi possivel conectar com o banco de dados. " + e.getMessage());
        }

    }

    public void createTable() {

        try (Statement statement = getConnection().createStatement()){
            Bukkit.getLogger().info("[ExtraCash] Carregando tabelas");

            statement.execute("CREATE TABLE IF NOT EXISTS accounts (" +
                    "id int(11) NOT NULL AUTO_INCREMENT, " +
                    "uuid char(36), " +
                    "balance int DEFAULT 0 NOT NULL," +
                    "PRIMARY KEY (id)" +
                    ") "
            );

            statement.execute("CREATE TABLE IF NOT EXISTS keys (" +
                    "id int (11) NOT NULL AUTO_INCREMENT," +
                    "key varchar(100)," +
                    "value int," +
                    "created_at timestamp DEFAULT CURRENT_TIMESTAMP," +
                    "used boolean DEFAULT false" +
                    "PRIMARY KEY (id)" +
                    ")"
            );

        }catch (SQLException e){
            Bukkit.getLogger().info("[ExtraCash] Erro Mysql");
        }

    }

    public void close(){

        if (getConnection() != null) {

            try {
                Bukkit.getLogger().info("[ExtraCash] Desativando conexao com o banco de dados");
                getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadAccounts(){

        try (Statement statement = connection.createStatement();
             ResultSet res = statement.executeQuery("SELECT * FROM accounts")){

            while (res.next()) {

                String uuid_ = res.getString("uuid");
                int balance =  res.getInt("balance");

                Account account = new Account(uuid_, balance);

                ExtraCash.getInstance().getAccountManager().accountMap.put(uuid_, account);

            }

        } catch (SQLException throwables) {

            Bukkit.getLogger().warning("[ExtraCash] Não foi possivel carregar os dados." + throwables.getMessage());

        }

    }

    public void saveAccounts(){

        AccountManager manager = ExtraCash.getInstance().getAccountManager();

        String queryUpdate = "UPDATE accounts SET balance = ? WHERE accounts.uuid = ?";
        String queryInsert = "INSERT INTO accounts (uuid, balance) VALUES (?, ?)";
        String querySelect = "SELECT * FROM accounts WHERE uuid = ?";

        for (Map.Entry<String, Account> accounts : manager.accountMap.entrySet()){

            try (PreparedStatement statement = connection.prepareStatement(querySelect)){

                statement.setString(1, accounts.getKey());

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()){

                    try (PreparedStatement statement2 = connection.prepareStatement(queryUpdate)){

                        statement2.setString(2, accounts.getKey());
                        statement2.setInt(1, accounts.getValue().getBalance());

                        statement2.executeUpdate();

                    }catch (SQLException e){
                        Bukkit.getLogger().warning("[ExtraCash] Erro ao salvar os dados. \n" + e.getMessage());
                    }

                }else {

                    try (PreparedStatement statement2 = connection.prepareStatement(queryInsert)){
                        statement2.setString(1, accounts.getKey());
                        statement2.setInt(2, accounts.getValue().getBalance());
                        statement2.executeUpdate();
                    }catch (SQLException e){
                        Bukkit.getLogger().warning("[ExtraCash] Erro ao salvar os dados. \n" + e.getMessage());
                    }

                }

            }catch (SQLException e){
                e.printStackTrace();
            }


        }


    }



}
