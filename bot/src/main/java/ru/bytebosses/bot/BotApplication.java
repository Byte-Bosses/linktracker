package ru.bytebosses.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.bytebosses.bot.api.endpoints.controllers.rateLimit.RateLimiterAspect;
import ru.bytebosses.bot.configuration.ApplicationConfig;
import ru.bytebosses.bot.configuration.RetryConfiguration;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfig.class, RateLimiterAspect.class, RetryConfiguration.class})
public class BotApplication {
    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }
}
