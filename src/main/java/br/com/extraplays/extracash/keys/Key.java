package br.com.extraplays.extracash.keys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class Key {

    String Key;
    boolean used;
    String createdAt;
    int value;


}
