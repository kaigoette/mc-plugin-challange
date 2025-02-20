package de.kaigoette.pluginKai.worldSwitcher;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;


public class WorldSwitcherListener implements Listener {
    
    private final WorldSwitcherMain worldSwitcherMain;
    
    public WorldSwitcherListener(WorldSwitcherMain worldSwitcherMain) {
        this.worldSwitcherMain = worldSwitcherMain;
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (new WorldSwitcherItem().equals(event.getPlayer().getInventory().getItemInMainHand())
                && (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)) {
            List<World> worlds = worldSwitcherMain.getWorlds().stream()
                    .filter(w -> w.members().stream().anyMatch(m -> m.equals(event.getPlayer().getName()))).toList();
            Inventory inventory = event.getPlayer().getServer().createInventory(null, 27, "Worlds");
            worlds.forEach(world -> inventory.addItem(new WorldItem(world.name())));
            event.getPlayer().openInventory(inventory);
        }
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCursor();
        Player player = event.getWhoClicked().getServer().getPlayer(event.getWhoClicked().getName());
        assert player != null;
        player.sendMessage(event.getWhoClicked().getName() + "You clicked: " + clickedItem.getItemMeta().getDisplayName());
        // Check if the clicked item is the one you are interested in
        if (clickedItem.getItemMeta() != null && new WorldItem(clickedItem.getItemMeta().getDisplayName()).equals(clickedItem)) {
            event.getWhoClicked().sendMessage("You clicked a world item!");
            worldSwitcherMain.joinWorld(player, clickedItem.getItemMeta().getDisplayName());
            event.setCancelled(true); // Cancel the event if necessary
        }
    }
    
}
