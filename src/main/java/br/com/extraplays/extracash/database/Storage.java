package br.com.extraplays.extracash.database;

import java.sql.Connection;

public interface Storage {

    void connect();

    void disconnect();

    Connection getConnection();

    void createTable();

}
