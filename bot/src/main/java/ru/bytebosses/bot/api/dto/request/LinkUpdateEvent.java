package ru.bytebosses.bot.api.dto.request;

import java.time.OffsetDateTime;
import java.util.Map;

public record LinkUpdateEvent(String type, OffsetDateTime updateTime, Map<String, String> metaInfo) {
}
