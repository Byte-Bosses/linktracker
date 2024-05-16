package ru.bytebosses.bot.configuration;

import ru.bytebosses.bot.api.httpClient.ScrapperClient;
import ru.bytebosses.bot.configuration.RetryConfiguration.Client;
import ru.bytebosses.bot.util.RetryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    @Bean
    public ScrapperClient scrapperClient(RetryConfiguration retryConfiguration) {
        return new ScrapperClient(RetryFactory.createRule(retryConfiguration.clientConfigs(), Client.SCRAPPER));
    }
}
