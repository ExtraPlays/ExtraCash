package br.com.extraplays.extracash.commands;

import br.com.extraplays.extracash.ExtraCash;
import br.com.extraplays.extracash.commands.executor.ExtraCommand;
import br.com.extraplays.extracash.keys.Key;
import br.com.extraplays.extracash.utils.ColorUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UseCommand extends ExtraCommand {


    public UseCommand(String usage) {
        super(usage);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player p = (Player) sender;

        if (args.length == 1) {

            Key key = keyManager.getKey(args[0]);

            if (key != null) {

                if (!key.isUsed()) {
                    accountManager.getAccount(p.getUniqueId().toString()).addBalance(key.getValue());
                    keyManager.setUsed(args[0]);
                    p.sendMessage(ColorUtil.colored("&7Você ganhou &6" + key.getValue() + " &7Cash. \n&aFaça bom uso!"));
                }else {
                    p.sendMessage(ColorUtil.colored("&cEssa KEY já foi usada!"));
                }

            }else {
                p.sendMessage(ColorUtil.colored("&cEssa KEY não existe!"));
            }

        }

    }
}
