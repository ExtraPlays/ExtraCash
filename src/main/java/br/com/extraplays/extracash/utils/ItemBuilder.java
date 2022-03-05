package br.com.extraplays.extracash.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {

    private final ItemStack itemStack;
    private ItemMeta itemMeta;
    private String name;
    private List<String> lore;
    private Material material;
    private Integer amount;

    /**
     *
     * @param material Material Type
     * @param amount
     * @param name
     * @param lore
     */
    public ItemBuilder(Material material, Integer amount, String name, List<String> lore){
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = itemStack.getItemMeta();
        this.itemMeta.setDisplayName(ColorUtil.colored(name));
        this.itemMeta.setLore(ColorUtil.colored(lore));
        this.itemStack.setItemMeta(itemMeta);
    }

    public ItemBuilder(Material material, Integer amount, String name){
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = itemStack.getItemMeta();
        this.itemMeta.setDisplayName(ColorUtil.colored(name));
        this.itemStack.setItemMeta(itemMeta);
    }

    public ItemBuilder(Material material, Integer amount){
        this.itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder(Material material, String name, List<String> lore){
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
        this.itemMeta.setDisplayName(ColorUtil.colored(name));
        this.itemMeta.setLore(ColorUtil.colored(lore));
        this.itemStack.setItemMeta(itemMeta);
    }

    public ItemBuilder(Material material, String name){
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
        this.itemMeta.setDisplayName(ColorUtil.colored(name));
        this.itemStack.setItemMeta(itemMeta);
    }

    public void setName(String name){
        this.itemMeta.setDisplayName(ColorUtil.colored(name));
        this.itemStack.setItemMeta(this.itemMeta);
    }

    public ItemStack build() {
        return this.itemStack;
    }
}