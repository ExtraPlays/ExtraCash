package br.com.extraplays.extracash.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
public class Account {

    @Getter @Setter private String uuid;
    @Getter @Setter public int balance;

}
