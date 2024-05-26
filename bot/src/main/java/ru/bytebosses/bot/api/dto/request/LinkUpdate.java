package ru.bytebosses.bot.api.dto.request;

import java.util.List;

public record LinkUpdate(LinkUpdateInformation linkUpdateInformation, List<Long> tgChatIds) {
}
