package ru.bytebosses.scrapper.configuration.extension

import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.bytebosses.extension.api.InformationProvider
import ru.bytebosses.extension.github.GithubInformationProvider
import ru.bytebosses.scrapper.provider.InformationProvidersRegistry
import java.net.URI
import java.time.OffsetDateTime

@Configuration
@ConditionalOnBean(InformationProvidersRegistry::class)
class GithubExtensionAutoConfiguration {

    @Bean
    fun githubExtension(informationProvidersRegistry: InformationProvidersRegistry): InformationProvider =
        GithubInformationProvider().apply {
            initialize()
            informationProvidersRegistry.registerInformationProvider("github", this)
        }

    @PostConstruct
    fun initialize() {
        GithubInformationProvider().apply {
            initialize()
            println(
                retrieveInformation(
                    URI("https://github.com/Byte-Bosses/linktracker"), hashMapOf(), OffsetDateTime.of(
                        2024, 5, 1, 0, 0, 0, 0, OffsetDateTime.now().offset
                    )
                )
            )
        }
    }

}
