package ru.bytebosses.extension.api

import java.net.URI
import java.time.OffsetDateTime

/**
 * Used to provide information about links
 */
interface InformationProvider {

    /**
     * Returns information about link in `LinkUpdateInformation`
     * @param uri link
     * @param metadata additional information to determine the novelty of the link
     * @param lastUpdate last update time
     */
    fun retrieveInformation(
        uri: URI,
        metadata: Map<String, String> = mapOf(),
        lastUpdate: OffsetDateTime = OffsetDateTime.now()
    ): LinkUpdateInformation

    /**
     * Returns true if link is supported with this provider
     * @param uri link
     */
    fun isSupported(uri: URI): Boolean

    /**
     * Initialize provider
     * Executes when scrapper load this provider
     */
    fun initialize()
}
