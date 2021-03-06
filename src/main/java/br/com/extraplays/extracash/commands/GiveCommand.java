package br.com.extraplays.extracash.commands;

import br.com.extraplays.extracash.account.Account;
import br.com.extraplays.extracash.commands.executor.ExtraCommand;
import br.com.extraplays.extracash.utils.ColorUtil;
import br.com.extraplays.extracash.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCommand extends ExtraCommand {

    public GiveCommand(String usage){
        super(usage);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length != 2){
            sender.sendMessage(MessageUtil.getMessage("incorrect-usage").replace("@usage", this.usage));
        }

        if (args.length == 2){

            Player p = (Player) sender;
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

            int amount;

            try {
                amount = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {

                p.sendMessage(MessageUtil.getMessage("number-exception"));
                return;
            }

            if (accountManager.hasAccount(p.getUniqueId().toString())){

                if (accountManager.hasAccount(target.getUniqueId().toString())){

                    if (!target.getUniqueId().toString().equals(p.getUniqueId().toString())) {

                        if (accountManager.getFormatedBalance(p.getUniqueId().toString()) >= amount) {
                            accountManager.getAccount(p.getUniqueId().toString()).removeBalance(amount);
                            accountManager.getAccount(target.getUniqueId().toString()).addBalance(amount);
                            sender.sendMessage(MessageUtil.getMessage("subcommand-give")
                                    .replace("@player", target.getName())
                                    .replace("@amount", String.valueOf(amount)));
                        }else {
                            p.sendMessage(MessageUtil.getMessage("insufficient-amount"));
                        }

                    }else {
                        p.sendMessage(MessageUtil.getMessage("is-yourself"));
                    }

                }else {
                    p.sendMessage(MessageUtil.getMessage("not-found"));
                }

            }
        }
    }
}
