package br.com.extraplays.extracash.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;


@AllArgsConstructor
public class Account implements Comparable<Account> {

    @Getter @Setter private String uuid;
    @Getter @Setter public int balance;

    @Override
    public int compareTo(@NotNull Account o) {
        return Integer.compare(o.balance, balance);
    }
}
