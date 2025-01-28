package de.kaigoette.pluginKai;

import de.kaigoette.pluginKai.MagicStick.MagicCommand;
import de.kaigoette.pluginKai.MagicStick.MagicStickListener;
import de.kaigoette.pluginKai.chatGPT.ChatGPTMain;
import de.kaigoette.pluginKai.explodingBall.BallMain;
import de.kaigoette.pluginKai.killFinn.KillCommand;
import de.kaigoette.pluginKai.killFinn.KillListener;
import de.kaigoette.pluginKai.scoreboard.ScoreboardMain;
import de.kaigoette.pluginKai.worldSwitcher.WorldSwitcherCommand;
import de.kaigoette.pluginKai.worldSwitcher.WorldSwitcherListener;
import de.kaigoette.pluginKai.worldSwitcher.WorldSwitcherMain;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    WorldSwitcherMain worldSwitcherMain;
    
    @Override
    public void onEnable() {
        // MagicStick
        getServer().getPluginManager().registerEvents(new MagicStickListener(), this);
        this.getCommand("magic").setExecutor(new MagicCommand());
        // Scoreboard
        new ScoreboardMain(this);
        // ExplodingBall
        new BallMain(this);
        //this.getCommand("kill").setExecutor(new KillCommand(this));
        //getServer().getPluginManager().registerEvents(new KillListener(this), this);
        
        new ChatGPTMain(this);
        worldSwitcherMain = new WorldSwitcherMain(this);
    }

    @Override
    public void onDisable() {
        worldSwitcherMain.saveData();
        // Plugin shutdown logic
    }
}
