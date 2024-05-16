package ru.bytebosses.bot.models;

import java.util.List;
import ru.bytebosses.bot.api.dto.request.LinkUpdate;

public record ListLinkUpdates(List<LinkUpdate> linkUpdates) {
}
