package ru.bytebosses.bot.api.dto.request;

import lombok.Getter;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public record LinkUpdateInformation(URI uri, String title, List<LinkUpdateEvent> events) {
    @Override
    public String toString() {
        return "LinkUpdateInformation{" +
            "uri=" + uri +
            ", title='" + title + '\'' +
            ", events=" + events +
            '}';
    }
}

