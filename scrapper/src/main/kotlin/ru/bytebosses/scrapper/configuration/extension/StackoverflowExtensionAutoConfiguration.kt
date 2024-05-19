package ru.bytebosses.scrapper.configuration.extension

import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.bytebosses.extension.api.InformationProvider
import ru.bytebosses.extension.stackoverflow.StackoverflowInformationProvider
import ru.bytebosses.scrapper.provider.InformationProvidersRegistry
import java.net.URI
import java.time.OffsetDateTime

@Configuration
@ConditionalOnBean(InformationProvidersRegistry::class)
class StackoverflowExtensionAutoConfiguration {

    @Bean
    fun stackoverflowExtension(informationProvidersRegistry: InformationProvidersRegistry): InformationProvider =
        StackoverflowInformationProvider().apply {
            initialize()
            informationProvidersRegistry.registerInformationProvider("stackoverflow", this)
        }

    @PostConstruct
    fun init() {
        StackoverflowInformationProvider().apply {
            initialize()
            println(retrieveInformation(
                URI("https://stackoverflow.com/questions/1770076"),
                hashMapOf(),
                OffsetDateTime.now()
            ))
        }
    }

}
