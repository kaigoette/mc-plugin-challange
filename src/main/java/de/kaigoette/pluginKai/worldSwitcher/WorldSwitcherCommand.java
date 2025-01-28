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
                    return removeWorld(player, player.getWorld().getName());
                }
                return false;
            } else if (args.length == 2) {
                switch (args[0]) {
                    case "add":
                        return addWorld(player, args[1], null);
                    case "join":
                        return joinWorld(player, args[1]);
                    case "invite":
                        return invitePlayer(player, player.getWorld().getName(), args[1]);
                    case "member":
                        player.sendMessage("Members of world " + args[1] + ":");
                        worldSwitcherMain.getWorlds().stream()
                                .filter(w -> w.name().equals(args[1]))
                                .findFirst()
                                .ifPresent(world1 -> world1.members().forEach(player::sendMessage));
                        return true;
                    case "remove":
                        return removeWorld(player, args[1]);
                }
                return false;
            } else if (args.length == 3) {
                
                switch (args[0]) {
                    case "invite":
                        return invitePlayer(player, args[2], args[1]);
                    case "kick":
                        return kickPlayer(player, args[1], args[2]);
                    case "add":
                        return addWorld(player, args[1], args[2]);
                }
                
                return false;
            }
        }
        return false;
    }
    
    private boolean invitePlayer(Player player, String worldName, String playerName) {
        World world = worldSwitcherMain.getWorlds().stream()
                .filter(w -> w.name().equals(worldName) && w.members().get(0).equals(player.getName()))
                .findFirst()
                .orElse(null);
        if(world != null) {
            world.members().add(playerName);
            player.sendMessage("Invited " + playerName + " to world " + worldName);
            player.getServer().getOnlinePlayers().stream()
                    .filter(p -> p.getName().equals(playerName))
                    .findFirst()
                    .ifPresent(p -> p.sendMessage("You were invited to world " + worldName));
            return true;
        } else {
            player.sendMessage("World not found!");
        }
        return false;
    }
    
    private boolean joinWorld(Player player, String worldName) {
        World joinWorld = worldSwitcherMain.getWorlds().stream()
                .filter(w -> w.name().equals(worldName))
                .findFirst()
                .orElse(null);
        if (joinWorld != null && (joinWorld.members().contains(player.getName()) || joinWorld.name().equals("world") || joinWorld.name().equals("world_nether") || joinWorld.name().equals("world_the_end"))) {
            player.teleport(player.getServer().getWorld(joinWorld.name()).getSpawnLocation());
            player.sendMessage("You joined the world " + joinWorld.name());
            return true;
        } else {
            player.sendMessage("World not found!");
        }
        return false;
    }
    
    private boolean addWorld(Player player, String worldName, String  seed) {
        if(player.getServer().getWorlds().stream()
                .anyMatch(world -> world.getName().equals(worldName))) {
            player.sendMessage("World already exists!");
            return false;
        }
        World addedWorld = new World(worldName, new ArrayList<>());
        addedWorld.members().add(player.getName());
        worldSwitcherMain.getWorlds().add(addedWorld);
        player.sendMessage("Creating world...");
        if (seed != null) {
            try {
                player.getServer().createWorld(new WorldCreator(worldName).seed(Long.parseLong(seed)));
            } catch (NumberFormatException e) {
                player.sendMessage("Invalid seed!");
                player.getServer().createWorld(new WorldCreator(worldName));
                return false;
            }
        } else {
            player.getServer().createWorld(new WorldCreator(worldName));
        }
        player.sendMessage("World added!");
        return true;
    }
    
    private boolean kickPlayer(Player player, String worldName, String playerName) {
        World world = worldSwitcherMain.getWorlds().stream()
                .filter(w -> w.name().equals(worldName) && w.members().get(0).equals(player.getName()))
                .findFirst()
                .orElse(null);
        if(world != null) {
            if(world.members().remove(playerName)) {
                player.sendMessage("Kicked " + playerName + " from world " + worldName);
                player.getServer().getOnlinePlayers().stream()
                        .filter(p -> p.getName().equals(playerName))
                        .findFirst()
                        .ifPresent(p -> p.sendMessage("You were kicked from world " + worldName));
                return true;
            } else {
                player.sendMessage("Player is not a member!");
            }
        } else {
            player.sendMessage("World not found!");
        }
        return false;
    }
    
    private boolean removeWorld(Player player, String worldName) {
        World removeWorld = worldSwitcherMain.getWorlds().stream()
                .filter(world ->
                        (!world.members().isEmpty() && world.members().contains(player.getName()))
                                && world.name().equals(worldName)
                )
                .findFirst().orElse(null);
        if(removeWorld != null) {
            worldSwitcherMain.getWorlds().remove(removeWorld);
            player.teleport(player.getServer().getWorld("world").getSpawnLocation());
            player.sendMessage("Removed your world!");
            return true;
        }
        return false;
    }
}
