package de.kaigoette.pluginKai.explodingBall;

import de.kaigoette.pluginKai.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class BallListener implements Listener {

    private final Main plugin;

    public BallListener(Main plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball) {
            Projectile projectile = event.getEntity();
            if (projectile.hasMetadata("explosionBall")) {
                Location hitLocation = projectile.getLocation();
                hitLocation.getWorld().createExplosion(hitLocation, 2.0F);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName().equals("BallThrower")) {
            Snowball snowball = player.launchProjectile(Snowball.class);
            snowball.setMetadata("explosionBall", new FixedMetadataValue(plugin, "BallThrower"));
        }
    }
}
