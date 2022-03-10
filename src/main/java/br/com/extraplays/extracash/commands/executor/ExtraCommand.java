package br.com.extraplays.extracash.commands.executor;

import br.com.extraplays.extracash.account.AccountManager;
import br.com.extraplays.extracash.ExtraCash;
import br.com.extraplays.extracash.keys.KeyManager;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public abstract class ExtraCommand {

    protected String usage;

    protected ExtraCommand(String usage){
        this.usage = usage;
    }

    public abstract void execute(CommandSender sender, String[] args);

    protected final FileConfiguration config = ExtraCash.getInstance().getConfig();
    protected final AccountManager accountManager = ExtraCash.getInstance().getAccountManager();
    protected final KeyManager keyManager = ExtraCash.getInstance().getKeyManager();

}
