package br.com.extraplays.extracash.commands;

import br.com.extraplays.extracash.commands.executor.ExtraCommand;
import br.com.extraplays.extracash.utils.ColorUtil;
import br.com.extraplays.extracash.utils.KeysUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KeysCommand extends ExtraCommand {

    public KeysCommand(String description) {
        super(description);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length != 2){
            sender.sendMessage(ColorUtil.colored("&7Uso correto: &a/cash keys create <amount>"));
        }

        Player p = (Player)sender;

        if (args[0].equalsIgnoreCase("create")) {

            int value = Integer.parseInt(args[1]);
            String key = KeysUtil.generateKey();

            p.sendMessage(ColorUtil.colored(""));
            p.sendMessage(ColorUtil.colored(" &7Nova &a&lKEY registrada: &a" + key));
            p.sendMessage(ColorUtil.colored(" &7Valor: &a" + value));
            p.sendMessage(ColorUtil.colored(" &7Nova &a&lKEY registrada: &a" + key));
            p.sendMessage(ColorUtil.colored(""));

        }

    }


}
