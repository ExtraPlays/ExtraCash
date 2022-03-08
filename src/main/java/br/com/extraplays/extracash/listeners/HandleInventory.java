package br.com.extraplays.extracash.listeners;

import br.com.extraplays.extracash.ExtraCash;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class HandleInventory implements Listener {

    FileConfiguration config = ExtraCash.getInstance().getConfig();

    @EventHandler
    public void handleInv(InventoryClickEvent event){

        Player player = (Player) event.getView().getPlayer();
        Inventory inventory = event.getClickedInventory();


    }

}
