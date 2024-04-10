package ru.bytebosses.scrapper.provider.api

import java.net.URI

interface InformationProvider {

    fun retrieveInformation(uri: URI): LinkUpdateInformation
    fun isSupported(uri: URI): Boolean
}
