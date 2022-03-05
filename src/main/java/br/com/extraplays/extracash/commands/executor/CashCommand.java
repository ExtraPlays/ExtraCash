package br.com.extraplays.extracash.commands.executor;

import br.com.extraplays.extracash.account.AccountManager;
import br.com.extraplays.extracash.commands.*;
import br.com.extraplays.extracash.ExtraCash;
import br.com.extraplays.extracash.utils.ColorUtil;
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
            .put("help", new InfoCommand("Command Help"))
            .put("add", new AddCommand( "adiciona cash para o player"))
            .put("remove", new RemoveCommand( "remove o cash de um player"))
            .put("set", new SetCommand("Seta o cash de um player"))
            .put("top", new TopCommand("mostra os players com mais cash do servidor"))
            .put("give", new GiveCommand("envia cash para um jogador"))
            .put("shop", new ShopCommand("Abre a loja de cash"))
            .put("keys", new KeysCommand("Keys"))
            .build();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;

        if (args.length == 0){

            Player p = (Player)sender;

            AccountManager accountManager = ExtraCash.getInstance().getAccountManager();

            if (accountManager.hasAccount(p.getUniqueId().toString())){

                sender.sendMessage(ColorUtil.colored("&6✪ &7Seu cash: &6" + accountManager.getFormatedBalance(p.getUniqueId().toString())));

            }else {

                accountManager.createAccount(p.getUniqueId().toString());
                sender.sendMessage(ColorUtil.colored("&6✪ &7Seu cash: &6" + accountManager.getFormatedBalance(p.getUniqueId().toString())));

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
