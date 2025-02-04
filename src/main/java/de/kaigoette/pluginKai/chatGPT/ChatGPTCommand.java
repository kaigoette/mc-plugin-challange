package de.kaigoette.pluginKai.chatGPT;

import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.Message;
import de.kaigoette.pluginKai.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ChatGPTCommand implements CommandExecutor {
    
    private final Main plugin;
    // private Map<UUID, List<ChatModel>> user = new HashMap<>();
    
    public ChatGPTCommand(Main plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage("Please provide a message to send to ChatGPT.");
                return false;
            }
            
            String message = String.join(" ", args);

            player.sendMessage("To ChatGPT: " + message);

            new BukkitRunnable() {
                @Override
                public void run() {
                    String response = sendMessageToChatGPT(message);
                    if (response != null) {
                        player.sendMessage("ChatGPT: " + response);
                    } else {
                        player.sendMessage("Failed to get a response from ChatGPT.");
                    }
                }
            }.runTaskAsynchronously(plugin);

            return true;
        }
        sender.sendMessage("This command can only be used by players.");
        return false;
    }

    private String sendMessageToChatGPT(String message) {

        ChatGPT chatGPT = ChatGPT.builder()
                .apiKey("API-KEY")
                .build()
                .init();
        
        String res = chatGPT.chat(message);

        return res;
    }
}