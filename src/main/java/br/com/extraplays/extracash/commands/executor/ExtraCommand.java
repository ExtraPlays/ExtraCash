package br.com.extraplays.extracash.commands.executor;

import br.com.extraplays.extracash.account.AccountManager;
import br.com.extraplays.extracash.ExtraCash;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public abstract class ExtraCommand {

    protected String description;

    protected ExtraCommand(String description){

        this.description = description;
    }

    public abstract void execute(CommandSender sender, String[] args);

    protected final FileConfiguration config = ExtraCash.getInstance().getConfig();
    protected final AccountManager accountManager = ExtraCash.getInstance().getAccountManager();

}
