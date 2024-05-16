package ru.bytebosses.extension.api

import java.net.URI

interface InformationProvider {
    fun retrieveInformation(uri: URI, metadata: Map<String, String>): LinkUpdateInformation
    fun isSupported(uri: URI): Boolean
    fun initialize()
}
