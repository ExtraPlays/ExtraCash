package br.com.extraplays.extracash.commands;

import br.com.extraplays.extracash.ExtraCash;
import br.com.extraplays.extracash.commands.executor.ExtraCommand;
import br.com.extraplays.extracash.keys.Key;
import br.com.extraplays.extracash.keys.KeyManager;
import br.com.extraplays.extracash.utils.ColorUtil;
import br.com.extraplays.extracash.utils.MessageUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KeysCommand extends ExtraCommand {

    public KeysCommand(String usage) {
        super(usage);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length != 2){

            if (sender.hasPermission("extracash.admin")){
                for (String message : ExtraCash.getInstance().getMessages().config().getStringList("subcommand-keys-help-admin")){
                    sender.sendMessage(ColorUtil.colored(message));
                }
            }else {
                for (String message : ExtraCash.getInstance().getMessages().config().getStringList("subcommand-keys-help")){
                    sender.sendMessage(ColorUtil.colored(message));
                }
            }

        }

        if (args.length == 2){

            Player p = (Player)sender;


            if (p.hasPermission("extracash.admin")){

                KeyManager keyManager = ExtraCash.getInstance().getKeyManager();

                if (args[0].equalsIgnoreCase("create")) {

                    int value = Integer.parseInt(args[1]);

                    Key key = keyManager.createKey(value, "teste", p.getName());

                    TextComponent message = new TextComponent(ColorUtil.colored(" &7Nova &a&lKEY &7registrada: &a&n" + key.getKey()));
                    message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Clique para copiar a key")));
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, key.getKey()));

                    p.sendMessage(ColorUtil.colored(""));
                    p.spigot().sendMessage(message);
                    p.sendMessage(ColorUtil.colored(" &7Valor: &a" + key.getValue()));
                    p.sendMessage(ColorUtil.colored(""));

                }

                if (args[0].equalsIgnoreCase("info")){

                    String keyString = args[1];

                    Key key = keyManager.getKey(keyString);

                    String used = key.isUsed() ? "Sim" : "Não";

                    TextComponent message = new TextComponent(ColorUtil.colored(" &7Informações da Key: &a&n" + key.getKey()));
                    message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            new Text(ColorUtil.colored("Criada em: &a" + key.getCreatedAt() + "\n&fValor: &a" + key.getValue() + "\n&fUsada: &a" + used))));

                    p.sendMessage(ColorUtil.colored(""));
                    p.spigot().sendMessage(message);
                    p.sendMessage(ColorUtil.colored(""));


                }

            }else {
                p.sendMessage(MessageUtil.getMessage("no-permission"));
            }


        }

    }


}
