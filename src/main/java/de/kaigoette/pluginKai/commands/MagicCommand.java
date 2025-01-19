package de.kaigoette.pluginKai.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MagicCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack magicStick = new ItemStack(Material.BLAZE_ROD);
            ItemMeta meta = magicStick.getItemMeta();
            if (meta != null) {
                meta.setDisplayName("Magic Stick");
                List<String> lore = new ArrayList<>();
                lore.add("A stick with magical powers");
                meta.setLore(lore);
                magicStick.setItemMeta(meta);
            }
            player.getInventory().addItem(magicStick);
            player.sendMessage("You have been given a Magic Stick!");
            return true;
        }
        sender.sendMessage("This command can only be used by players.");
        return false;
    }
}