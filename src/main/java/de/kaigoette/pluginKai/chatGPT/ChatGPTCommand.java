package de.kaigoette.pluginKai.chatGPT;

import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.Message;
import de.kaigoette.pluginKai.Main;
import de.kaigoette.pluginKai.chatGPT.models.ChatGPTUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ChatGPTCommand implements CommandExecutor {
    
    private final Main plugin;
    private final ChatGPTMain chatGPTMain;
    // private Map<UUID, List<ChatModel>> user = new HashMap<>();
    
    public ChatGPTCommand(ChatGPTMain chatGPTMain, Main plugin) {
        this.plugin = plugin;
        this.chatGPTMain = chatGPTMain;
        
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String apiKey = chatGPTMain.data.users().stream()
                    .filter(user -> user.name().equals(player.getName()))
                    .map(ChatGPTUser::apiKey)
                    .findFirst()
                    .orElse(null);
            
            if (args.length == 0) {
                player.sendMessage("Please provide a message to send to ChatGPT.");
                return false;
            } 
            else if (args.length >= 2 && args[0].equals("apikey")) {
                String newApiKey = args[1];
                Optional<ChatGPTUser> userOptional = chatGPTMain.data.users().stream()
                        .filter(user -> user.name().equals(player.getName()))
                        .findFirst();

                if (userOptional.isPresent()) {
                    ChatGPTUser user = userOptional.get();
                    chatGPTMain.data.users().remove(user);
                    chatGPTMain.data.users().add(new ChatGPTUser(user.name(), newApiKey));
                    chatGPTMain.saveData();
                    player.sendMessage("API key updated successfully.");
                } else {
                    chatGPTMain.data.users().add(new ChatGPTUser(player.getName(), newApiKey));
                    chatGPTMain.saveData();
                    player.sendMessage("API key added successfully.");
                }
                return true;
            } else {
                
                String message = String.join(" ", args);

                player.sendMessage("To ChatGPT: " + message);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        String response = sendMessageToChatGPT(message, apiKey);
                        if (response != null) {
                            player.sendMessage("ChatGPT: " + response);
                        } else {
                            player.sendMessage("Failed to get a response from ChatGPT.");
                        }
                    }
                }.runTaskAsynchronously(plugin);

                return true;
            }
            
        }
        sender.sendMessage("This command can only be used by players.");
        return false;
    }

    private String sendMessageToChatGPT(String message, String apiKey) {

        ChatGPT chatGPT = ChatGPT.builder()
                .apiKey(apiKey)
                .build()
                .init();
        
        String res = chatGPT.chat(message);

        return res;
    }
}