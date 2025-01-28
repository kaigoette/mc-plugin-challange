package de.kaigoette.pluginKai.worldSwitcher;

import java.util.List;
import java.util.UUID;

public record World(
        String name,
        List<String> members
) {
}
