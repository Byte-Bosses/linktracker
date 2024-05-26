package ru.bytebosses.bot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.bytebosses.bot.api.httpclient.ScrapperClient;
import ru.bytebosses.bot.configuration.RetryConfiguration.Client;
import ru.bytebosses.bot.util.RetryFactory;

@Configuration
public class ClientConfig {

    @Value("${client.scrapper.url}")
    private String scrapperUrl;

    @Bean
    public ScrapperClient scrapperClient(RetryConfiguration retryConfiguration) {
        return new ScrapperClient(
            scrapperUrl,
            RetryFactory.createRule(retryConfiguration.clientConfigs(), Client.SCRAPPER)
        );
    }
}
