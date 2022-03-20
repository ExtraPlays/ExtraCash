package br.com.extraplays.extracash.keys;

import org.apache.commons.lang.RandomStringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class KeyManager {

    public List<Key> KeysList = new ArrayList<>();
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy H:m");

    public Key getKey(String keyString){

        Key key = null;
        for (Key k : KeysList) {
            if (k.getKey().equals(keyString)) {
                key = k;
            }
        }

        return key;
    }

    public Key createNewKey(int value){
        Key key = new Key(RandomStringUtils.random(10, "abcdefghijklmnopqrstuvwxyz123456789"), false, dateFormat.format(new Date()), value);
        KeysList.add(key);
        return key;
    }

    public void setUsed(String key){
        Key k = getKey(key);
        k.setUsed(true);
    }




}
