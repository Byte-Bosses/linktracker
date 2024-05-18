package ru.bytebosses.scrapper.configuration.integration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatusCode
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import reactor.core.publisher.Mono
import ru.bytebosses.scrapper.integration.bot.BotClient

@Configuration
class BotClientConfiguration {

    @Value("\${app.bot.url}")
    lateinit var botUrl: String

    @Bean
    fun botClient(): BotClient {
        val webClient = WebClient.builder()
            .defaultStatusHandler(
                { _: HttpStatusCode? -> true },
                { _: ClientResponse? -> Mono.empty() })
            .defaultHeader("Content-Type", "application/json")
            .baseUrl(botUrl).build()

        val httpServiceProxyFactory = HttpServiceProxyFactory
            .builderFor(WebClientAdapter.create(webClient))
            .build()
        return httpServiceProxyFactory.createClient(BotClient::class.java)
    }

}
