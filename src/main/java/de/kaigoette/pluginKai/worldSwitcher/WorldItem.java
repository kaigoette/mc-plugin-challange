package de.kaigoette.pluginKai.worldSwitcher;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WorldItem extends ItemStack {
    public WorldItem(String worldName) {
        super(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(worldName);
        List<String> lore = new ArrayList<>();
        lore.add("Click to join the world");
        meta.setLore(lore);
        setItemMeta(meta);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ItemStack) {
            ItemStack item = (ItemStack) obj;
            return !item.getItemMeta().getLore().isEmpty() && item.getItemMeta().getLore().get(0).equals(this.getItemMeta().getLore().get(0));
        }
        return false;
    }
}
