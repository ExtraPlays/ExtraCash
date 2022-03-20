package br.com.extraplays.extracash.listeners;

import br.com.extraplays.extracash.ExtraCash;
import br.com.extraplays.extracash.account.AccountManager;
import br.com.extraplays.extracash.utils.ActionBarUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;

public class EntityDeathListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){

        if (!ExtraCash.getInstance().getConfig().getBoolean("mobs.enable")) return;

        Player killer = e.getEntity().getKiller();

        if (e.getEntity().getKiller() == null) return;
        
        
        
        ConfigurationSection section = ExtraCash.getInstance().getConfig().getConfigurationSection("mobs.mobs");
        AccountManager accountManager = ExtraCash.getInstance().getAccountManager();
        int chance = ExtraCash.getInstance().getConfig().getInt("mobs.chance");

        for (String s : section.getKeys(false)){

            if (e.getEntityType() == EntityType.valueOf(s)) {

                Random r = new Random();
                int chan = r.nextInt(100) + 1;
                int amount = section.getInt(s);

                if (chan < chance){
                    accountManager.getAccount(killer.getUniqueId().toString()).addBalance(amount);
                    ActionBarUtil.sendActionBar(killer, "&a+ &6" + amount + " ✪");
                }

            }

        }


    }

}
