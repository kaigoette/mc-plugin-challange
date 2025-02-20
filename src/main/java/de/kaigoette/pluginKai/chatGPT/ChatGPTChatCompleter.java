package de.kaigoette.pluginKai.chatGPT;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ChatGPTChatCompleter implements TabCompleter {
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if(args.length == 1) {
            completions.add("apikey");
            completions = filterStartingWith(args[0], completions);
        }
        if(args.length >= 1 && !args[0].equals("apikey")) completions.add("Your message to ChatGPT");
        return completions;
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
