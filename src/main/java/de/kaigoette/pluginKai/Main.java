package de.kaigoette.pluginKai;

import de.kaigoette.pluginKai.MagicStick.MagicCommand;
import de.kaigoette.pluginKai.MagicStick.MagicStickListener;
import de.kaigoette.pluginKai.explodingBall.BallMain;
import de.kaigoette.pluginKai.killFinn.KillCommand;
import de.kaigoette.pluginKai.killFinn.KillListener;
import de.kaigoette.pluginKai.scoreboard.ScoreboardMain;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // MagicStick
        getServer().getPluginManager().registerEvents(new MagicStickListener(), this);
        this.getCommand("magic").setExecutor(new MagicCommand());
        // Scoreboard
        new ScoreboardMain(this);
        // ExplodingBall
        new BallMain(this);
        // OPFAAA
        this.getCommand("kill").setExecutor(new KillCommand(this));
        getServer().getPluginManager().registerEvents(new KillListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
