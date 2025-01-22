package de.kaigoette.pluginKai.explodingBall;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BallItem extends ItemStack {
    //Exploding snowball

    public BallItem() {
        super(Material.CLAY_BALL);
        ItemMeta meta = getItemMeta();
        if (meta != null) {
            meta.setDisplayName("BallThrower");
            List<String> lore = new ArrayList<>();
            lore.add("A snowball-thrower that explodes on impact!");
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
