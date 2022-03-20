package br.com.extraplays.extracash.commands;

import br.com.extraplays.extracash.commands.executor.ExtraCommand;
import br.com.extraplays.extracash.utils.ColorUtil;
import br.com.extraplays.extracash.utils.MessageUtil;
import br.com.extraplays.extracash.utils.NumberUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveCommand extends ExtraCommand{

    public RemoveCommand(String usage) {
        super(usage);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length != 2){
            sender.sendMessage(MessageUtil.getMessage("incorrect-usage").replace("@usage", this.usage));
        }

        if (!sender.hasPermission("extracash.admin")) {
            sender.sendMessage(MessageUtil.getMessage("no-permission"));
            return;
        }

        if (args.length == 2) {

            // 0 = player, 1 = ammount
            Player p = (Player)sender;
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

            int amount;

            try {
                amount = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {

                p.sendMessage(MessageUtil.getMessage("number-exception"));
                return;
            }

            if(accountManager.hasAccount(target.getUniqueId().toString())){

                accountManager.getAccount(target.getUniqueId().toString()).removeBalance(amount);

                p.sendMessage(MessageUtil.getMessage("subcommand-remove")
                        .replace("@player", target.getName())
                        .replace("@amount", String.valueOf(amount)));

            }else {
                p.sendMessage(MessageUtil.getMessage("not-found"));
            }


        }

    }
}
