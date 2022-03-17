package br.com.extraplays.extracash.database;

import br.com.extraplays.extracash.account.Account;
import br.com.extraplays.extracash.account.AccountManager;
import br.com.extraplays.extracash.ExtraCash;
import br.com.extraplays.extracash.keys.Key;
import br.com.extraplays.extracash.keys.KeyManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.Map;

public class DatabaseManager {

    private final Storage storage;
    private final KeyManager keyManager = ExtraCash.getInstance().getKeyManager();

    public DatabaseManager(Storage storage){
        this.storage = storage;
    }

    public void loadAccounts(){

        try (Statement statement = storage.getConnection().createStatement();
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

    public void loadKeys(){

        try (Statement statement = storage.getConnection().createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM `keys`")) {

            while (res.next()){

                Key key = new Key();
                key.setKey(res.getString("key"));
                key.setValue(res.getInt("value"));
                key.setUsed(res.getBoolean("used"));

                keyManager.KeysList.add(key);

            }


        }catch (SQLException e){
            Bukkit.getLogger().warning("[ExtraCash] Não foi possivel carregar as keys. \n" + e.getMessage());
        }

    }

    public void saveKeys(){

        String querySelect = "SELECT * from `keys` WHERE `key` = ?";
        String queryInsert = "INSERT INTO `keys` (`key`, value, used) VALUES (?,?,?)";
        String queryUpdate = "UPDATE `keys` SET used = ? WHERE `key` = ?";

        for (Key k : keyManager.KeysList) {

            try(PreparedStatement statement = storage.getConnection().prepareStatement(querySelect)){

                statement.setString(1, k.getKey());

                ResultSet res = statement.executeQuery();
                if (res.next()) {
//                    UPDATE
                    try (PreparedStatement statement2 = storage.getConnection().prepareStatement(queryUpdate)){

                        statement2.setBoolean(1, k.isUsed());
                        statement2.setString(2, k.getKey());

                        statement2.executeUpdate();

                    }catch (SQLException e){
                        e.printStackTrace();
                    }

                }else {
//                    INSERT
                    try (PreparedStatement statement2 = storage.getConnection().prepareStatement(queryInsert)){

                        statement2.setString(1, k.getKey());
                        statement2.setInt(2, k.getValue());
                        statement2.setBoolean(3, k.isUsed());
                        statement2.executeUpdate();

                    }catch (SQLException e){
                        e.printStackTrace();
                    }

                }

                res.close();

            }catch (SQLException e){
                e.printStackTrace();
            }

        }

    }

    public void saveAccounts(){

        AccountManager manager = ExtraCash.getInstance().getAccountManager();

        String queryUpdate = "UPDATE accounts SET balance = ? WHERE accounts.uuid = ?";
        String queryInsert = "INSERT INTO accounts (uuid, balance) VALUES (?, ?)";
        String querySelect = "SELECT * FROM accounts WHERE uuid = ?";

        for (Map.Entry<String, Account> accounts : manager.accountMap.entrySet()){

            try (PreparedStatement statement = storage.getConnection().prepareStatement(querySelect)){

                statement.setString(1, accounts.getKey());

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()){

                    try (PreparedStatement statement2 = storage.getConnection().prepareStatement(queryUpdate)){

                        statement2.setString(2, accounts.getKey());
                        statement2.setInt(1, accounts.getValue().getBalance());

                        statement2.executeUpdate();

                    }catch (SQLException e){
                        Bukkit.getLogger().warning("[ExtraCash] Erro ao salvar os dados. \n" + e.getMessage());
                    }

                }else {

                    try (PreparedStatement statement2 = storage.getConnection().prepareStatement(queryInsert)){
                        statement2.setString(1, accounts.getKey());
                        statement2.setInt(2, accounts.getValue().getBalance());
                        statement2.executeUpdate();
                    }catch (SQLException e){
                        Bukkit.getLogger().warning("[ExtraCash] Erro ao salvar os dados. \n" + e.getMessage());
                    }

                }

                resultSet.close();

            }catch (SQLException e){
                e.printStackTrace();
            }


        }


    }



}
