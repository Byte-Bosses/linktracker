package ru.bytebosses.bot.api.dto.response;

import ru.bytebosses.bot.models.Link;
import java.util.List;

public record ListLinksResponse(List<Link> links) {}
