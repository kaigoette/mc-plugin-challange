package de.kaigoette.pluginKai.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.*;

public class ScoreboardListener implements Listener {

    private final Scoreboard board;
    private final Objective objective;

    public ScoreboardListener(Scoreboard board, Objective objective) {
        this.board = board;
        this.objective = objective;
    }


    public void resetScore() {
        for (String entry : board.getEntries()) {
            board.resetScores(entry);
        }
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setScoreboard(board);
        objective.getScore(player.getName()).setScore(0);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        board.resetScores(event.getPlayer().getName());
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killed = event.getEntity();
        Player killer = killed.getKiller();
        if (killer != null) {
            Score score = objective.getScore(killer.getName());
            score.setScore(score.getScore() + 1);
        }
    }
}
