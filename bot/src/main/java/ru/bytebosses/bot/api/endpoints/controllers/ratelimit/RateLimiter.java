package ru.bytebosses.bot.api.endpoints.controllers.ratelimit;

import io.github.bucket4j.Bucket;
import java.time.Duration;

/**
 * RateLimiter class is used for configuring bucket with tokens
 **/

public class RateLimiter {
    private final Bucket bucket;

    public RateLimiter(
        Integer maxTokens, Integer amountOfTokensGeneratedPerPeriod, Duration period
    ) {
        this.bucket = Bucket.builder()
            .addLimit(limit -> limit.capacity(maxTokens).refillGreedy(amountOfTokensGeneratedPerPeriod, period))
            .build();
    }

    /**
     * Tries to consume a token from the bucket.
     *
     * @return true if a token was successfully consumed, false otherwise
     */
    public boolean tryConsumeToken() {
        return bucket.tryConsume(1);
    }
}
