package br.com.extraplays.extracash.commands;

import br.com.extraplays.extracash.account.Account;
import br.com.extraplays.extracash.commands.executor.ExtraCommand;
import br.com.extraplays.extracash.utils.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCommand extends ExtraCommand {

    public GiveCommand(String Description){
        super(Description);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length != 2){
            sender.sendMessage(ColorUtil.colored("&7Uso correto: &a/cash give <player> <amount> "));
        }

        if (args.length == 2){

            Player p = (Player) sender;
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            int amount = Integer.parseInt(args[1]);
            if (accountManager.hasAccount(p.getUniqueId().toString())){

                if (accountManager.hasAccount(target.getUniqueId().toString())){

                    if (!target.getUniqueId().toString().equals(p.getUniqueId().toString())) {

                        if (accountManager.getFormatedBalance(p.getUniqueId().toString()) >= amount) {
                            accountManager.removeBalance(p.getUniqueId().toString(), amount);
                            accountManager.addBalance(target.getUniqueId().toString(), amount);
                            p.sendMessage(ColorUtil.colored("&7Você enviou &6" + amount + " &7para o player &6" + target.getName()));
                        }else {
                            p.sendMessage(ColorUtil.colored("&cVocê não possui essa quantidade de cash"));
                        }

                    }else {
                        p.sendMessage(ColorUtil.colored("&cVocê não pode enviar cash para você mesmo."));
                    }

                }else {
                    p.sendMessage(ColorUtil.colored("&cO player não foi encontrado"));
                }

            }else {

            }

        }

    }
}
