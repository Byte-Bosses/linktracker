package ru.bytebosses.extension.api

import java.net.URI
import java.time.OffsetDateTime

interface InformationProvider {
    fun retrieveInformation(
        uri: URI,
        metadata: Map<String, String> = mapOf(),
        lastUpdate: OffsetDateTime = OffsetDateTime.now()
    ): LinkUpdateInformation

    fun isSupported(uri: URI): Boolean
    fun initialize()
}
