package de.kaigoette.pluginKai.worldSwitcher;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

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
            event.getPlayer().sendMessage("Startet..");
            worldSwitcherMain.saveData();
            event.getPlayer().sendMessage("Gespeichert!");
        }
    }
    
    
    
}
