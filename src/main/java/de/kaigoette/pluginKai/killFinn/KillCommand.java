package de.kaigoette.pluginKai.killFinn;

import de.kaigoette.pluginKai.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KillCommand implements CommandExecutor {
    
    Main plugin;
    
    public KillCommand(Main plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        plugin.getServer().getPlayer("finnimcfinnfinn").setHealth(0);
        plugin.getServer().broadcastMessage("haha OPFAAA");
        return false;
    }
}
