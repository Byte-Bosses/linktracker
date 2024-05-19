package ru.bytebosses.scrapper.configuration.extension

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.bytebosses.extension.api.InformationProvider
import ru.bytebosses.extension.stackoverflow.StackoverflowInformationProvider
import ru.bytebosses.scrapper.provider.InformationProvidersRegistry

@Configuration
@ConditionalOnBean(InformationProvidersRegistry::class)
class StackoverflowExtensionAutoConfiguration {

    @Bean
    fun stackoverflowExtension(informationProvidersRegistry: InformationProvidersRegistry): InformationProvider =
        StackoverflowInformationProvider().apply {
            initialize()
            informationProvidersRegistry.registerInformationProvider("stackoverflow", this)
        }

}
