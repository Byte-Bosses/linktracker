package ru.bytebosses.bot.util;

import java.time.Duration;
import java.util.function.Predicate;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

@RequiredArgsConstructor
public final class LinearRetryBackoffSpec extends Retry {
    private static final Duration MAX_BACKOFF = Duration.ofMillis(Long.MAX_VALUE);
    private final Duration minBackoff;
    private final Duration maxBackoff;
    private final Double multiplier;
    private final int maxAttempts;
    private final Predicate<Throwable> errorFilter;
    private final Supplier<Scheduler> schedulerSupplier;


    /**
     * Creates a new LinearRetryBackoffSpec by filtering errors.
     *
     * @param  errorFilter  the Predicate to filter errors
     * @return              a LinearRetryBackoffSpec with filtered errors
     */
    public LinearRetryBackoffSpec filter(Predicate<Throwable> errorFilter) {
        return new LinearRetryBackoffSpec(
            this.minBackoff,
            this.maxBackoff,
            this.multiplier,
            this.maxAttempts,
            errorFilter,
            this.schedulerSupplier
        );
    }

    /**
     * Generates a LinearRetryBackoffSpec with the given parameters.
     *
     * @param  maxAttempts  the maximum number of retry attempts
     * @param  minDelay     the minimum delay duration
     * @return              a new LinearRetryBackoffSpec instance
     */
    public static LinearRetryBackoffSpec linear(int maxAttempts, Duration minDelay) {
        return new LinearRetryBackoffSpec(
            minDelay,
            MAX_BACKOFF,
            1.0,
            maxAttempts,
            e -> true,
            Schedulers::parallel
        );
    }

    /**
     * Generates a new LinearRetryBackoffSpec with the specified multiplier.
     *
     * @param  multiplier   the multiplier value to set
     * @return              a new LinearRetryBackoffSpec with updated multiplier
     */
    public LinearRetryBackoffSpec multiplier(Double multiplier) {
        return new LinearRetryBackoffSpec(
            this.minBackoff,
            this.maxBackoff,
            multiplier,
            this.maxAttempts,
            this.errorFilter,
            this.schedulerSupplier
        );
    }

    /**
     * Generate a companion Publisher based on retry signals.
     *
     * @param  retrySignals   the Flux of RetrySignal instances
     * @return                a Publisher of unknown type
     */
    @Override
    public Publisher<?> generateCompanion(Flux<RetrySignal> retrySignals) {
        return Flux.deferContextual(cv ->
            retrySignals.contextWrite(cv)
                .concatMap(retryWhenState -> {
                    RetrySignal copy = retryWhenState.copy();
                    Throwable currentFailure = copy.failure();
                    long iteration = copy.totalRetries();
                    if (currentFailure == null) {
                        return Mono.error(new IllegalStateException(
                            "Retry.RetrySignal#failure() not expected to be null"));
                    }
                    if (!errorFilter.test(currentFailure)) {
                        return Mono.error(currentFailure);
                    }
                    if (iteration >= maxAttempts) {
                        return Mono.error(new IllegalStateException("Retry exhausted: " + this));
                    }
                    Duration nextBackoff;
                    try {
                        nextBackoff = minBackoff.multipliedBy((long) (iteration * multiplier));
                        if (nextBackoff.compareTo(maxBackoff) > 0) {
                            nextBackoff = maxBackoff;
                        }
                    } catch (ArithmeticException overflow) {
                        nextBackoff = maxBackoff;
                    }

                    return Mono.delay(nextBackoff, schedulerSupplier.get()).contextWrite(cv);
                })
                .onErrorStop()
        );
    }
}
