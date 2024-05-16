package ru.bytebosses.bot.api.dto.request;

import java.net.URI;
import java.util.List;

public record LinkUpdateInformation(URI uri, String title, List<LinkUpdateEvent> events) {
    @Override
    public String toString() {
        return "LinkUpdateInformation{"
            + "uri=" + uri
            + ", title='" + title + '\''
            + ", events=" + events
            + '}';
    }
}

