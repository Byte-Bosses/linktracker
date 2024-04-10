package ru.bytebosses.scrapper.provider.api

import java.net.URI
import java.util.*

interface InformationProvidersRegistry {

    fun registerInformationProvider(id: String, provider: InformationProvider)
    fun getInformationProvider(id: String): InformationProvider
    fun findInformationProvider(uri: URI): Optional<InformationProvider>

}
