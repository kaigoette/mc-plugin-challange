package de.kaigoette.pluginKai.chatGPT;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ChatGPTDataManager {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static <T> void saveToJsonFile(File file, T data) {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> String saveToJsonFile(T data) {
        return gson.toJson(data);
    }

    public static <T> T loadFromJsonFile(File file, Class<T> clazz) {
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
