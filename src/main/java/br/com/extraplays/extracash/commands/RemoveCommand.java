package br.com.extraplays.extracash.commands;

import br.com.extraplays.extracash.commands.executor.ExtraCommand;
import br.com.extraplays.extracash.utils.ColorUtil;
import br.com.extraplays.extracash.utils.NumberUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class RemoveCommand extends ExtraCommand{

    public RemoveCommand(String description) {
        super(description);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length != 2){
            sender.sendMessage(ColorUtil.colored("&7Uso correto: &a/cash remove <player> <amount>"));
        }

        if (args.length == 2) {

            // 0 = player, 1 = ammount

            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            int amount = Integer.parseInt(args[1]);

            if(accountManager.hasAccount(player.getUniqueId().toString())){

                accountManager.removeBalance(player.getUniqueId().toString(), amount);

                sender.sendMessage(ColorUtil.colored("&7Você removeu &6" + amount + " Cash &7do player &6" + player.getName()));

            }


        }

    }
}
