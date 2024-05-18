package ru.bytebosses.bot.util;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.experimental.UtilityClass;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;
import reactor.util.retry.RetryBackoffSpec;
import ru.bytebosses.bot.configuration.RetryConfiguration.Client;
import ru.bytebosses.bot.configuration.RetryConfiguration.RetryMode;
import ru.bytebosses.bot.configuration.RetryConfiguration.RetrySpecification;
import ru.bytebosses.bot.models.RetryRule;

@UtilityClass
public class RetryFactory {
    private final Map<RetryMode, Function<RetrySpecification, RetryRule>> retryRules = Map.of(
        RetryMode.FIXED, retrySpec -> new RetryRule(
            RetryBackoffSpec
                .fixedDelay(
                    retrySpec.maxAttempts(),
                    retrySpec.minDelay()
                ),
            retrySpec.codes()
        ),
        RetryMode.LINEAR, retrySpec -> new RetryRule(
            LinearRetryBackoffSpec
                .linear(
                    retrySpec.maxAttempts(),
                    retrySpec.minDelay()
                )
                .multiplier(retrySpec.multiplier()),
            retrySpec.codes()
        ),
        RetryMode.EXPONENTIAL, retrySpec -> new RetryRule(
            RetryBackoffSpec
                .backoff(
                    retrySpec.maxAttempts(),
                    retrySpec.minDelay()
                )
                .maxBackoff(retrySpec.maxDelay()),
            retrySpec.codes()
        )
    );

    /**
     * Creates an ExchangeFilterFunction with a retry rule.
     *
     * @param  retry   the retry rule to apply
     * @return        the created ExchangeFilterFunction
     */
    public static ExchangeFilterFunction createFilter(RetryRule retry) {
        return (response, next) -> next.exchange(response)
            .flatMap(clientResponse -> {
                if (retry.codes().contains(clientResponse.statusCode().value())) {
                    return clientResponse.createError();
                } else {
                    return Mono.just(clientResponse);
                }
            }).retryWhen(retry.rule());
    }

    /**
     * Creates a RetryRule based on the provided RetrySpecification list and client.
     *
     * @param  specificationList  the list of RetrySpecifications to create the rule from
     * @param  client             the client for which the rule is being created
     * @return                    the created RetryRule based on the specifications and client
     */
    public static RetryRule createRule(List<RetrySpecification> specificationList, Client client) {
        return specificationList.stream()
            .filter(a -> a.client().equals(client))
            .findFirst()
            .map(a -> retryRules.get(a.mode()).apply(a))
            .orElse(
                new RetryRule(
                    RetryBackoffSpec.fixedDelay(1, Duration.ZERO),
                    List.of()
                )
            );
    }
}
