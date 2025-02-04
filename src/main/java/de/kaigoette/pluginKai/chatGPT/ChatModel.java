package de.kaigoette.pluginKai.chatGPT;

import java.util.List;
import java.util.UUID;

public record ChatModel(
        List<MessageModel> messages
) {
}
