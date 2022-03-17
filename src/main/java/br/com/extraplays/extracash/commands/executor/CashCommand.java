package br.com.extraplays.extracash.commands.executor;

import br.com.extraplays.extracash.account.AccountManager;
import br.com.extraplays.extracash.commands.*;
import br.com.extraplays.extracash.ExtraCash;
import br.com.extraplays.extracash.utils.MessageUtil;
import com.google.common.collect.ImmutableMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;

public class CashCommand implements CommandExecutor {

    private final Map<String, ExtraCommand> commands = ImmutableMap.<String, ExtraCommand>builder()
            .put("help", new HelpCommand(""))
            .put("add", new AddCommand( "/cash add <player> <amount>"))
            .put("remove", new RemoveCommand( "/cash remove <player> <amount>"))
            .put("set", new SetCommand("/cash set <player> <amount>"))
            .put("top", new TopCommand("mostra os players com mais cash do servidor"))
            .put("give", new GiveCommand("/cash give <player> <amount>"))
//            .put("shop", new ShopCommand("/cash shop"))
            .put("keys", new KeysCommand("/cash keys"))
            .put("use", new UseCommand("/cash use key"))
            .build();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        if (args.length == 0){

            Player p = (Player)sender;

            AccountManager accountManager = ExtraCash.getInstance().getAccountManager();

            if (accountManager.hasAccount(p.getUniqueId().toString())){

                sender.sendMessage(MessageUtil.getMessage("command-cash")
                        .replace("@player", p.getName())
                        .replace("@amount", String.valueOf(accountManager.getFormatedBalance(p.getUniqueId().toString()))));

            }else {

                accountManager.createAccount(p.getUniqueId().toString());
                sender.sendMessage(MessageUtil.getMessage("command-cash")
                        .replace("@player", p.getName())
                        .replace("@amount", String.valueOf(accountManager.getFormatedBalance(p.getUniqueId().toString()))));

            }

            return false;

        }

        ExtraCommand extraCommand = commands.get(args[0].toLowerCase());


        if (extraCommand == null){
            sender.sendMessage("oi2");
            return false;
        }

        extraCommand.execute(sender, Arrays.copyOfRange(args, 1, args.length));
        return false;

    }
}
