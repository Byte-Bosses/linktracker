package ru.bytebosses.scrapper.configuration.extension

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.bytebosses.scrapper.provider.ExtensionInformationProviderRegistry
import ru.bytebosses.scrapper.provider.InformationProvidersRegistry
import kotlin.io.path.Path

@Configuration
class ExtensionConfiguration {

    @Value("\${extensions.path:extensions}")
    private lateinit var extensionsPath: String

    @Bean
    fun extensionInformationProviderRegistry(): InformationProvidersRegistry {
        return ExtensionInformationProviderRegistry(directory = Path(extensionsPath))
    }

}
