package br.com.extraplays.extracash.commands;

import br.com.extraplays.extracash.ExtraCash;
import br.com.extraplays.extracash.commands.executor.ExtraCommand;
import br.com.extraplays.extracash.utils.ColorUtil;
import br.com.extraplays.extracash.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShopCommand extends ExtraCommand {

    public ShopCommand(String usage) {
        super(usage);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player p = (Player) sender;
        FileConfiguration config = ExtraCash.getInstance().getShop().config();

        Inventory inv = Bukkit.createInventory(null, config.getInt("rows") * 9, ColorUtil.colored(config.getString("title")));

        ConfigurationSection section = config.getConfigurationSection("items");

        for (String s : section.getKeys(false)) {


            List<String> lore = new ArrayList<>();

            for (String l : section.getStringList(s + ".lore")){
                lore.add(l.replace("@price", String.valueOf(section.getInt(s  + ".price"))));
            }

            ItemStack itemStack = new ItemBuilder(Material.matchMaterial(section.getString(s + ".material")),
                    section.getInt(s + ".amount"),
                    section.getString(s + ".name"),
                    lore).build();

            for (String en : section.getStringList(s + ".enchants")){

                String enchant = en.split(":")[0];
                int level =  Integer.parseInt(en.split(":")[1]);

                ItemMeta meta = itemStack.getItemMeta();

                meta.addEnchant(Enchantment.getByName(enchant.toUpperCase()), level, false);

                itemStack.setItemMeta(meta);

            }

            inv.setItem(section.getInt(s + ".slot"), itemStack);

        }

        p.openInventory(inv);




    }
}
