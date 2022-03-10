package br.com.extraplays.extracash.keys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KeyManager {

    public List<Key> KeysList = new ArrayList<>();

    public Key getKey(String keyString){

        Key key = null;
        for (Key k : KeysList) {
            if (k.getKey().equals(keyString)) {
                key = k;
            }
        }

        return key;
    }

    public Key createKey(int value, String expiryDate, String createdBy){

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy H:m");

        Key key = new Key();
        key.setValue(value);
        key.setCreatedAt(dateFormat.format(date));
        key.setCreatedBy(createdBy);
        KeysList.add(key);

        return key;

    }

    public void setUsed(String key){

        Key k = getKey(key);
        k.setUsed(true);
        KeysList.remove(k);
        KeysList.add(k);

    }




}
