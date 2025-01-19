package de.kaigoette.pluginKai;

import de.kaigoette.pluginKai.commands.MagicCommand;
import de.kaigoette.pluginKai.listener.PlayerInteractListener;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        createMagicStick();
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        this.getCommand("magic").setExecutor(new MagicCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void createMagicStick() {
        ItemStack magicStick = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = magicStick.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("Magic Stick");
            List<String> lore = new ArrayList<>();
            lore.add("A stick with magical powers");
            meta.setLore(lore);
            magicStick.setItemMeta(meta);
        }
    }
}
