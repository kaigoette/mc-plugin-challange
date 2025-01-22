package de.kaigoette.pluginKai.explodingBall;

import de.kaigoette.pluginKai.Main;
import de.kaigoette.pluginKai.scoreboard.ScoreboardCommand;

public class BallMain {
    
    private final Main plugin;
    
    public BallMain(Main plugin) {
        this.plugin = plugin;
        BallListener listener = new BallListener(plugin);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        plugin.getCommand("getExplosionBall").setExecutor(new BallCommand());
    }
    
}
