package de.kaigoette.pluginKai.chatGPT;

import de.kaigoette.pluginKai.Main;

public class ChatGPTMain {
    
    public ChatGPTMain(Main plugin) {
        plugin.getCommand("chatgpt").setExecutor(new ChatGPTCommand(plugin));
        
    }
    
}
