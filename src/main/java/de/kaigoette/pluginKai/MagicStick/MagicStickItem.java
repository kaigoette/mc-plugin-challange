package de.kaigoette.pluginKai.MagicStick;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MagicStickItem extends ItemStack {
    
        public MagicStickItem() {
            super(Material.BLAZE_ROD);
            ItemMeta meta = getItemMeta();
            if (meta != null) {
                meta.setDisplayName("Magic Stick");
                List<String> lore = new ArrayList<>();
                lore.add("A magical stick that can transform blocks!");
                meta.setLore(lore);
                setItemMeta(meta);
            }
        }
        
        public boolean equals(Object obj) {
            if (obj instanceof ItemStack) {
                ItemStack item = (ItemStack) obj;
                return item.getType() == 
                        Material.BLAZE_ROD && 
                        item.getItemMeta() != null 
                        && this.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())
                        && this.getItemMeta().getLore().equals(item.getItemMeta().getLore());
            }
            return false;
        }
    
}
