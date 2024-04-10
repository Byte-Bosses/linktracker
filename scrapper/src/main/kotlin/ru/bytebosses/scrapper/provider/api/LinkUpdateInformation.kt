package ru.bytebosses.scrapper.provider.api

import java.net.URI

data class LinkUpdateInformation(
    val uri: URI,
    val title: String,
    val events: List<LinkUpdateEvent>,
    val metaInfo: Map<String, String>
)

data class LinkUpdateEvent(
    var type: String
) {

}
