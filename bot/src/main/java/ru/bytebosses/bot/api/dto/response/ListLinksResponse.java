package ru.bytebosses.bot.api.dto.response;

import java.util.List;
import ru.bytebosses.bot.models.Link;

public record ListLinksResponse(List<Link> links) {}
