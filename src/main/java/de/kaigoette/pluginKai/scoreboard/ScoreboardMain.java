package de.kaigoette.pluginKai.scoreboard;

import de.kaigoette.pluginKai.MagicStick.MagicCommand;
import de.kaigoette.pluginKai.MagicStick.MagicStickListener;
import de.kaigoette.pluginKai.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.net.http.WebSocket;

public class ScoreboardMain {
    
    final private ScoreboardManager manager;
    final private Scoreboard board;
    final private Objective objective;
    final private Main plugin;
    
    public ScoreboardMain (Main plugin) {
        this.plugin = plugin;
        manager = Bukkit.getScoreboardManager();
        board = manager.getNewScoreboard();
        objective = board.registerNewObjective("test", "dummy", ChatColor.GREEN + "Kills");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        
        plugin.getServer().getOnlinePlayers().forEach(
                player -> player.setScoreboard(board));
        plugin.getServer().getOnlinePlayers().forEach(
                player -> objective.getScore(player.getName()).setScore(0));
        
        ScoreboardListener listener = new ScoreboardListener(
                board, objective);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        plugin.getCommand("resetscore").setExecutor(new ScoreboardCommand(this));
    }
    
    public void resetScore() {
        for (String entry : board.getEntries()) {
            board.resetScores(entry);
        }
        plugin.getServer().getOnlinePlayers().forEach(
                player -> objective.getScore(player.getName()).setScore(0));
    }
}
