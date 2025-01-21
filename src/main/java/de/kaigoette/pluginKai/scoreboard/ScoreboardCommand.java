package de.kaigoette.pluginKai.scoreboard;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ScoreboardCommand implements CommandExecutor {
    
    final ScoreboardMain scoreboardMain;
    
    public ScoreboardCommand(ScoreboardMain scoreboardMain) {
        this.scoreboardMain = scoreboardMain;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        scoreboardMain.resetScore();
        return false;
    }
}
