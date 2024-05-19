package ru.bytebosses.scrapper.provider

import ru.bytebosses.extension.api.InformationProvider
import java.net.URI
import java.util.*

/**
 * Holds information providers
 */
interface InformationProvidersRegistry {
    fun registerInformationProvider(id: String, provider: InformationProvider)
    fun getInformationProvider(id: String): InformationProvider

    /**
     * Finds information provider by uri, returns empty if not found
     */
    fun findInformationProvider(uri: URI): Optional<InformationProvider>
}
