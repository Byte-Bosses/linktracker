package ru.bytebosses.bot.models;

import java.util.List;
import reactor.util.retry.Retry;

public record RetryRule(Retry rule, List<Integer> codes) {
}
