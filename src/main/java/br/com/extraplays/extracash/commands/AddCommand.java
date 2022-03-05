package br.com.extraplays.extracash.commands;

import br.com.extraplays.extracash.commands.executor.ExtraCommand;
import br.com.extraplays.extracash.utils.ColorUtil;
import br.com.extraplays.extracash.utils.NumberUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class AddCommand extends ExtraCommand {

    public AddCommand(String description) {
        super(description);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length != 2){
            sender.sendMessage(ColorUtil.colored("&7Uso correto: &a/cash add <player> <amount> "));
        }

        if (args.length == 2) {

            // 0 = player, 1 = ammount

            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            int amount = Integer.parseInt(args[1]);

            if(accountManager.hasAccount(player.getUniqueId().toString())){

                accountManager.addBalance(player.getUniqueId().toString(), amount);

                sender.sendMessage(ColorUtil.colored("&7Você adicionou &6" + amount + " Cash &7para o player &6" + player.getName()));

            }


        }

    }

}
