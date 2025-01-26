package de.kaigoette.pluginKai.worldSwitcher;

import de.kaigoette.pluginKai.Main;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;


public class WorldSwitcherListener implements Listener {
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (new WorldSwitcherItem().equals(event.getPlayer().getInventory().getItemInMainHand())
                && (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)) {
            event.getPlayer().sendMessage("Teleporting to your world...");
            World world = null;
            List<World> worlds = event.getPlayer().getServer().getWorlds();
            for (int i = 0; i < worlds.size(); i++) {
                if(worlds.get(i).getName().equals("world_" + event.getPlayer().getName())) {
                    world = worlds.get(i);
                }
            }
            if(world == null)
                world = event.getPlayer().getServer().createWorld(new WorldCreator("world_" + event.getPlayer().getName()));
            
            if(event.getPlayer().getWorld().getName().equals(world.getName())) 
                event.getPlayer().teleport(event.getPlayer().getServer().getWorld("world").getSpawnLocation());
            else event.getPlayer().teleport(world.getSpawnLocation());
        }
    }
    
}
