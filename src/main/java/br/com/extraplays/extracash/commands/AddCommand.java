package br.com.extraplays.extracash.commands;

import br.com.extraplays.extracash.commands.executor.ExtraCommand;
import br.com.extraplays.extracash.utils.ColorUtil;
import br.com.extraplays.extracash.utils.MessageUtil;
import br.com.extraplays.extracash.utils.NumberUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class AddCommand extends ExtraCommand {

    public AddCommand(String usage) {
        super(usage);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length != 2){
            sender.sendMessage(MessageUtil.getMessage("incorrect-usage").replace("@usage", this.usage));
        }

        if (args.length == 2) {

            // 0 = player, 1 = ammount

            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            int amount = Integer.parseInt(args[1]);

            if(accountManager.hasAccount(player.getUniqueId().toString())){

                accountManager.addBalance(player.getUniqueId().toString(), amount);

                sender.sendMessage(MessageUtil.getMessage("subcommand-add")
                        .replace("@player", player.getName())
                        .replace("@amount", String.valueOf(amount)));

            }


        }

    }

}
