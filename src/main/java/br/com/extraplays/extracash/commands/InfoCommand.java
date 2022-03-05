package br.com.extraplays.extracash.commands;

import br.com.extraplays.extracash.commands.executor.ExtraCommand;
import br.com.extraplays.extracash.utils.ColorUtil;
import org.bukkit.command.CommandSender;

public class InfoCommand extends ExtraCommand {


    public InfoCommand(String description) {
        super(description);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length == 0){

            sender.sendMessage(ColorUtil.colored("&a[ExtraCash] &7Versão: &a1.0"));
            if (sender.hasPermission("extracash.admin")){
                sender.sendMessage(ColorUtil.colored("&7/cash add <player> <amount> - &aAdiciona uma quantia para um jogador"));
                sender.sendMessage(ColorUtil.colored("&7/cash remove <player> <amount> - &aRemove uma quantia de um jogador" ));
                sender.sendMessage(ColorUtil.colored("&7/cash set <player> <amount> - &aDefine a quantia de coins de um jogar"));
            }
            sender.sendMessage(ColorUtil.colored("&7/cash - &aMostra o seu cash"));
            sender.sendMessage(ColorUtil.colored("&7/cash top - &aMostra os mais ricos do servidor"));



        }

    }
}
