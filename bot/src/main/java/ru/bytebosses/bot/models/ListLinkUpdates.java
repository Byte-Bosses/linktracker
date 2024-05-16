package ru.bytebosses.bot.models;

import ru.bytebosses.bot.api.dto.request.LinkUpdate;

import java.util.List;

public record ListLinkUpdates(List<LinkUpdate> linkUpdates) {
}
