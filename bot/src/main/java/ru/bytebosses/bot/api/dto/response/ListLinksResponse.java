package ru.bytebosses.bot.api.dto.response;

import java.util.List;
import ru.bytebosses.bot.models.LinkResponse;

public record ListLinksResponse(List<LinkResponse> linkResponses) {}
