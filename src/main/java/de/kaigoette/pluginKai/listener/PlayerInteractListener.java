package de.kaigoette.pluginKai.listener;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item != null && item.getType() == Material.BLAZE_ROD && item.getItemMeta() != null && "Magic Stick".equals(item.getItemMeta().getDisplayName())) {
                Block block = event.getClickedBlock();
                if (block != null) {
                    Material[] materials = Material.values();
                    Random random = new Random();
                    Material randomMaterial;
                    do {
                        randomMaterial = materials[random.nextInt(materials.length)];
                    } while (!randomMaterial.isBlock());
                    block.setType(randomMaterial);
                    block.setType(randomMaterial);

                    // Generate random RGB values
                    float red = random.nextFloat();
                    float green = random.nextFloat();
                    float blue = random.nextFloat();

                    // Spawn particle at block location
                    block.getWorld().spawnParticle(Particle.BLOCK, block.getLocation().add(new Vector(0.5, 0.5, 0.5)), 10, new Particle.DustOptions(org.bukkit.Color.fromRGB((int) (red * 255), (int) (green * 255), (int) (blue * 255)), 1));
                }
            }
        }
    }
}