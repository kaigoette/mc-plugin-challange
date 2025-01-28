package de.kaigoette.pluginKai.worldSwitcher;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class WorldSwitcherTabCompleter implements TabCompleter {

    private final WorldSwitcherMain worldSwitcherMain;
    private final List<String> subCommands = Arrays.asList("add", "join", "invite", "kick", "list", "member", "remove");

    public WorldSwitcherTabCompleter(WorldSwitcherMain worldSwitcherMain) {
        this.worldSwitcherMain = worldSwitcherMain;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                return filterStartingWith(args[0], subCommands);
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("member")) {
                    return filterStartingWith(args[1], worldSwitcherMain.getWorlds().stream()
                            .filter(world -> world.members().contains(player.getName())  || world.name().equals("world") || world.name().equals("world_nether") || world.name().equals("world_the_end"))
                            .map(World::name)
                            .toList());
                } else if (args[0].equalsIgnoreCase("invite") || args[0].equalsIgnoreCase("kick")) {
                    // Return list of player names
                    return filterStartingWith(args[1], player.getServer().getOnlinePlayers().stream().map(Player::getName).toList());
                } else if (args[0].equalsIgnoreCase("kick") || args[0].equalsIgnoreCase("kick")) {
                    // Return list of player names
                    return filterStartingWith(args[1], worldSwitcherMain.getWorlds().stream()
                            .filter(world -> world.members().contains(player.getName()))
                            .map(World::name)
                            .toList());
                } else if (args[0].equalsIgnoreCase("add")) {
                    List<String> options = new ArrayList<>();
                    options.add("World name");
                    return options;
                } else if (args[0].equalsIgnoreCase("remove")) {
                    return filterStartingWith(args[1], worldSwitcherMain.getWorlds().stream()
                            .filter(world -> !world.members().isEmpty() && world.members().getFirst().equals(player.getName()))
                            .map(World::name)
                            .toList());
                }
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("kick")) {
                    // Return list of player names
                    return filterStartingWith(args[2], findMemberOfWorld(args[1]));
                } else if (args[0].equalsIgnoreCase("invite")) {
                    // Return list of player names
                    return filterStartingWith(args[2], filterStartingWith(args[2], worldSwitcherMain.getWorlds().stream()
                            .filter(world -> !world.members().isEmpty() && world.members().getFirst().equals(player.getName()))
                            .map(World::name)
                            .toList()));
                } else if (args[0].equalsIgnoreCase("add")) {
                    List<String> options = new ArrayList<>();
                    options.add("Seed");
                    return options;
                }
                
            }
        }
        return new ArrayList<>();
    }

    public List<String> findMemberOfWorld(String name) {
        Optional<World> found = worldSwitcherMain.getWorlds().stream()
                .filter(world -> world.name().equals(name))
                .findFirst();
        
        return found.isPresent() ? found.get().members() 
            : new ArrayList<>();
    }
    
    private List<String> filterStartingWith(String prefix, List<String> options) {
        List<String> result = new ArrayList<>();
        for (String option : options) {
            if (option.toLowerCase().startsWith(prefix.toLowerCase())) {
                result.add(option);
            }
        }
        return result;
    }
}
