package ru.bytebosses.scrapper.provider.api

import java.net.URI

interface InformationProvider {
    fun retrieveInformation(uri: URI, metadata: Map<String, String>): LinkUpdateInformation
    fun isSupported(uri: URI): Boolean
}
