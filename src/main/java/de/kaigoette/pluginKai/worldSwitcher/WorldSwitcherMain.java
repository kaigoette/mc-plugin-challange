package de.kaigoette.pluginKai.worldSwitcher;

import de.kaigoette.pluginKai.Main;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class WorldSwitcherMain {
    
    private final List<World> worlds;
    private File dataFile;
    WorldSwitcherDataManager jsonFileUtil;
    final Main plugin;

    public WorldSwitcherMain(Main plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "worlds.json");
        this.jsonFileUtil = new WorldSwitcherDataManager();

        if (!dataFile.exists()) {
            plugin.getDataFolder().mkdirs();
            this.worlds = new ArrayList<>();
            worlds.add(new World("world", new ArrayList<>()));
            worlds.add(new World("world_nether", new ArrayList<>()));
            worlds.add(new World("world_the_end", new ArrayList<>()));
            jsonFileUtil.saveToJsonFile(dataFile, new Worlds(worlds));
        } else {
            List<World> loadedWorlds = jsonFileUtil.loadFromJsonFile(dataFile, Worlds.class).worlds();
            if (loadedWorlds != null && !loadedWorlds.isEmpty()) {
                this.worlds = loadedWorlds;
            } else {
                this.worlds = new ArrayList<>();
                worlds.add(new World("world", new ArrayList<>()));
                worlds.add(new World("world_nether", new ArrayList<>()));
                worlds.add(new World("world_the_end", new ArrayList<>()));
            }
        }

        plugin.getServer().getPluginManager().registerEvents(new WorldSwitcherListener(this), plugin);
        plugin.getCommand("world").setExecutor(new WorldSwitcherCommand(this));
        plugin.getCommand("world").setTabCompleter(new WorldSwitcherTabCompleter(this));
    }

    public void saveData() {
        jsonFileUtil.saveToJsonFile(dataFile, new Worlds(worlds));
        try {
            dataFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<World> getWorlds() {
        return worlds;
    }
    
    public void setWorlds(List<World> worlds) {
        this.worlds.clear();
        this.worlds.addAll(worlds);
    }

    public boolean invitePlayer(Player player, String worldName, String playerName) {
        World world = this.getWorlds().stream()
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

    public boolean joinWorld(Player player, String worldName) {
        World joinWorld = this.getWorlds().stream()
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

    public boolean addWorld(Player player, String worldName, String  seed) {
        if(player.getServer().getWorlds().stream()
                .anyMatch(world -> world.getName().equals(worldName))) {
            player.sendMessage("World already exists!");
            return false;
        }
        World addedWorld = new World(worldName, new ArrayList<>());
        addedWorld.members().add(player.getName());
        this.getWorlds().add(addedWorld);
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

    public boolean kickPlayer(Player player, String worldName, String playerName) {
        World world = this.getWorlds().stream()
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

    public boolean removeWorld(Player player, String worldName) {
        World removeWorld = this.getWorlds().stream()
                .filter(world ->
                        (!world.members().isEmpty() && world.members().contains(player.getName()))
                                && world.name().equals(worldName)
                )
                .findFirst().orElse(null);
        if(removeWorld != null) {
            this.getWorlds().remove(removeWorld);
            player.teleport(player.getServer().getWorld("world").getSpawnLocation());
            player.sendMessage("Removed your world!");
            return true;
        }
        return false;
    }
}
