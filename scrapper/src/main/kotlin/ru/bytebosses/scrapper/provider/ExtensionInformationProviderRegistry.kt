package ru.bytebosses.scrapper.provider

import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import ru.bytebosses.extension.api.ExtensionProvider
import ru.bytebosses.extension.api.InformationProvider
import ru.bytebosses.extension.infrastructure.loader.ExtensionLoader
import java.net.URI
import java.nio.file.Path
import java.util.*

/**
 * Holds information providers loaded from extensions with jar or by uri
 */
class ExtensionInformationProviderRegistry(
    private val providers: MutableMap<String, InformationProvider> = hashMapOf(),
    private val directory: Path = Path.of("extensions")
) : InformationProvidersRegistry {

    private val logger = LoggerFactory.getLogger("ExtensionInformationProviderRegistry")
    override fun registerInformationProvider(id: String, provider: InformationProvider) {
        providers[id] = provider
    }

    override fun getInformationProvider(id: String): InformationProvider {
        return providers[id] ?: throw IllegalArgumentException("Provider $id not found")
    }

    override fun findInformationProvider(uri: URI): Optional<InformationProvider> {
        return Optional.ofNullable(providers.values.firstOrNull { it.isSupported(uri) })
    }


    @PostConstruct
    fun initialize() {
        logger.info("#################################################")
        logger.info("Starting extension initialization")

        val extensionLoader = ExtensionLoader()
        val extensions = extensionLoader.loadAllExtensions(directory.toAbsolutePath())
        extensions.forEach {
            registerInformationProvider(
                it.javaClass.getAnnotation(ExtensionProvider::class.java).name, it
            )
        }
        providers.entries.forEach {
            it.value.initialize()
            logger.info("Provider ${it.key} initialized")
        }
        logger.info("Extension initialization completed")
        logger.info("#################################################")
    }
}
