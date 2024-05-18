package ru.bytebosses.scrapper.provider.api

import java.net.URI
import java.time.OffsetDateTime

interface InformationProvider {
    fun retrieveInformation(uri: URI, metadata: Map<String, String>, lastUpdate: OffsetDateTime): LinkUpdateInformation
    fun isSupported(uri: URI): Boolean
}
