package br.com.extraplays.extracash.database;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StorageType {

    SQLITE("SQLite"),
    MYSQL("MySQL");

    private final String name;

}
