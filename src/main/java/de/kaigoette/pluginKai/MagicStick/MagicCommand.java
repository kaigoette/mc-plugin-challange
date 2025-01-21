package de.kaigoette.pluginKai.MagicStick;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MagicCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.getInventory().addItem(new MagicStickItem());
            player.sendMessage("You have been given a Magic Stick!");
            return true;
        }
        sender.sendMessage("This command can only be used by players.");
        return false;
    }
}