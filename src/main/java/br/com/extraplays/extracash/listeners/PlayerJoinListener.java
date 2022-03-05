package br.com.extraplays.extracash.listeners;

import br.com.extraplays.extracash.ExtraCash;
import br.com.extraplays.extracash.account.AccountManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent e){

        AccountManager manager = ExtraCash.getInstance().getAccountManager();
        Player p = e.getPlayer();

        if (!manager.hasAccount(p.getUniqueId().toString())) {
            manager.createAccount(p.getUniqueId().toString());
        }


    }

}
