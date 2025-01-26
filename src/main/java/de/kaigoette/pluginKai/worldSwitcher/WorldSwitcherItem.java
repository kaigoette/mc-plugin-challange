package de.kaigoette.pluginKai.worldSwitcher;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WorldSwitcherItem extends ItemStack {
    public WorldSwitcherItem() {
        super(Material.COMPASS);
        ItemMeta meta = getItemMeta();
        meta.setDisplayName("World Switcher");
        List<String> lore = new ArrayList<>();
        lore.add("Right click to switch worlds");
        meta.setLore(lore);
        setItemMeta(meta);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ItemStack) {
            ItemStack item = (ItemStack) obj;
            return item.getType() == Material.COMPASS 
                    && item.getItemMeta().getDisplayName().equals(this.getItemMeta().getDisplayName())
                    && item.getItemMeta().getLore().equals(this.getItemMeta().getLore());
        }
        return false;
    }
}
