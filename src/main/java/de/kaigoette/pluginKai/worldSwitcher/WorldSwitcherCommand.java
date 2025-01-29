package de.kaigoette.pluginKai.worldSwitcher;

import com.google.common.annotations.VisibleForTesting;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class WorldSwitcherCommand implements CommandExecutor {
    
    private final WorldSwitcherMain worldSwitcherMain;
    
    public WorldSwitcherCommand(WorldSwitcherMain worldSwitcherMain) {
        this.worldSwitcherMain = worldSwitcherMain;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            
            Player player = (Player) sender;
            
            if(args.length == 0) {
                player.getInventory().addItem(new WorldSwitcherItem());
                return true;
            } else if (args.length == 1) {
                if (args[0].equals("list")) {
                    player.sendMessage("Your worlds:");
                    worldSwitcherMain.getWorlds().stream()
                            .filter(world -> world.members().contains(player.getName()))
                            .forEach(world -> player.sendMessage(world.name()));
                    player.sendMessage("world");
                    return true;
                } else if(args[0].equals("member")) {
                    player.sendMessage("Members of your world:");
                    worldSwitcherMain.getWorlds().stream()
                            .filter(world -> world.name().equals(player.getWorld().getName()) && !world.name().equals("world"))
                            .findFirst()
                            .ifPresent(world -> world.members().forEach(player::sendMessage));
                    return true;
                } else if (args[0].equals("remove")) {
                    return worldSwitcherMain.removeWorld(player, player.getWorld().getName());
                }
                return false;
            } else if (args.length == 2) {
                switch (args[0]) {
                    case "add":
                        return worldSwitcherMain.addWorld(player, args[1], null);
                    case "join":
                        return worldSwitcherMain.joinWorld(player, args[1]);
                    case "invite":
                        return worldSwitcherMain.invitePlayer(player, player.getWorld().getName(), args[1]);
                    case "member":
                        player.sendMessage("Members of world " + args[1] + ":");
                        worldSwitcherMain.getWorlds().stream()
                                .filter(w -> w.name().equals(args[1]))
                                .findFirst()
                                .ifPresent(world1 -> world1.members().forEach(player::sendMessage));
                        return true;
                    case "remove":
                        return worldSwitcherMain.removeWorld(player, args[1]);
                }
                return false;
            } else if (args.length == 3) {
                
                switch (args[0]) {
                    case "invite":
                        return worldSwitcherMain.invitePlayer(player, args[2], args[1]);
                    case "kick":
                        return worldSwitcherMain.kickPlayer(player, args[1], args[2]);
                    case "add":
                        return worldSwitcherMain.addWorld(player, args[1], args[2]);
                }
                
                return false;
            }
        }
        return false;
    }
}
