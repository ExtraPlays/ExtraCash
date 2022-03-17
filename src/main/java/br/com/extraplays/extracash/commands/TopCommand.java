package br.com.extraplays.extracash.commands;

import br.com.extraplays.extracash.account.Account;
import br.com.extraplays.extracash.commands.executor.ExtraCommand;
import br.com.extraplays.extracash.utils.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TopCommand extends ExtraCommand {

    public TopCommand(String usage) {
        super(usage);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player p = (Player) sender;

        StringBuilder sb = new StringBuilder("&7[&a$&7] &8&l➡ &fCash TOP \n\n");
        int pos = 0;

        for (Account acc : accountManager.getRanking()){

            pos++;
            OfflinePlayer topPlayer = Bukkit.getOfflinePlayer(UUID.fromString(acc.getUuid()));
            sb.append("&7[&6" + pos + "&7] &f" + topPlayer.getName() + " - &a" + acc.getBalance() + "\n");

        }

        p.sendMessage(ColorUtil.colored(sb.toString()));




    }
}
