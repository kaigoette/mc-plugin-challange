package de.kaigoette.pluginKai.chatGPT.models;

import java.util.List;

public record ChatGPTData(
        List<ChatGPTUser> users
) {
}
