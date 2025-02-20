package de.kaigoette.pluginKai.chatGPT;

import de.kaigoette.pluginKai.Main;
import de.kaigoette.pluginKai.chatGPT.models.ChatGPTData;
import de.kaigoette.pluginKai.worldSwitcher.WorldSwitcherTabCompleter;

import java.io.File;
import java.util.ArrayList;

public class ChatGPTMain {
    
    private File dataFile;
    public ChatGPTData data;
    public ChatGPTDataManager jsonFileUtil;
    
    public ChatGPTMain(Main plugin) {
        
        this.dataFile = new File(plugin.getDataFolder(), "chatGPT.json");
        this.jsonFileUtil = new ChatGPTDataManager();
        if (!dataFile.exists()) {
            plugin.getDataFolder().mkdirs();
            jsonFileUtil.saveToJsonFile(dataFile, new ChatGPTData(new ArrayList<>()));
        } else {
            ChatGPTData daten = jsonFileUtil.loadFromJsonFile(dataFile, ChatGPTData.class);
            if (daten != null && !daten.users().isEmpty()) {
                this.data = daten;
            } else {
                this.data = new ChatGPTData(new ArrayList<>());
            }
        }
        plugin.getCommand("chatgpt").setExecutor(new ChatGPTCommand(this, plugin));
        plugin.getCommand("chatgpt").setTabCompleter(new ChatGPTChatCompleter());
        
    }
    
    public void saveData() {
        jsonFileUtil.saveToJsonFile(dataFile, data);
        try {
            dataFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
