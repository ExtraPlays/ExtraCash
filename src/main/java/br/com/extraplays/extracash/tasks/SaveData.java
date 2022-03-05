package br.com.extraplays.extracash.tasks;

import br.com.extraplays.extracash.ExtraCash;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveData extends BukkitRunnable {

    @Override
    public void run() {

        ExtraCash.getInstance().getDatabaseManager().saveAccounts();
        Bukkit.getLogger().info("[ExtraCash] Salvando dados na database.");

    }
}
