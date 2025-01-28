package de.kaigoette.pluginKai.worldSwitcher;

import de.kaigoette.pluginKai.Main;

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
}
