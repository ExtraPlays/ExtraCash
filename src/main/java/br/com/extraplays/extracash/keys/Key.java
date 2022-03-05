package br.com.extraplays.extracash.keys;

import lombok.Getter;
import lombok.Setter;

public class Key {

    @Getter @Setter String Key;
    @Getter @Setter boolean used;
    @Getter @Setter String createdAt, expiryTime, createdBy;
    @Getter @Setter int value;

    public Key(){
        String[] characters = {
                "0", "1", "b", "2", "4", "5", "6", "7", "8", "9",
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
                "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
                "x", "y", "z"
        };

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int position = (int) (Math.random() * characters.length);
            sb.append(characters[position]);
        }

         setKey(sb.toString());
    }


}
