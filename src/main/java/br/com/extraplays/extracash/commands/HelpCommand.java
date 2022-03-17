package br.com.extraplays.extracash.commands;

import br.com.extraplays.extracash.ExtraCash;
import br.com.extraplays.extracash.commands.executor.ExtraCommand;
import br.com.extraplays.extracash.utils.ColorUtil;
import br.com.extraplays.extracash.utils.MessageUtil;
import org.bukkit.command.CommandSender;

public class HelpCommand extends ExtraCommand {


    public HelpCommand(String description) {
        super(description);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length == 0){
            if (sender.hasPermission("extracash.admin")){
                for (String message : ExtraCash.getInstance().getMessages().config().getStringList("subcommand-help-admin")){
                    sender.sendMessage(ColorUtil.colored(message));
                }
            }else {
                for (String message : ExtraCash.getInstance().getMessages().config().getStringList("subcommand-help")){
                    sender.sendMessage(ColorUtil.colored(message));
                }
            }
        }
    }
}
