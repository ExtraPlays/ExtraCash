package br.com.extraplays.extracash.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;


@AllArgsConstructor
@Data
public class Account implements Comparable<Account> {

    private String uuid;
    private int balance;

    public void removeBalance(int amount){
        balance -= amount;
    }

    public void addBalance(int amount){
        balance += amount;
    }

    @Override
    public int compareTo(@NotNull Account o) {
        return Integer.compare(o.balance, balance);
    }
}
