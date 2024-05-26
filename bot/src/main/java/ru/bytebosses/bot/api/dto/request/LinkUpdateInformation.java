package ru.bytebosses.bot.api.dto.request;

import java.net.URI;
import java.util.List;

public record LinkUpdateInformation(URI uri, List<LinkUpdateEvent> events) {
}

