package ru.bytebosses.bot.models;

import reactor.util.retry.Retry;
import java.util.List;

public record RetryRule(Retry rule, List<Integer> codes) {
}
