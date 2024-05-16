package ru.bytebosses.extension.api

import java.net.URI
import java.time.OffsetDateTime

data class LinkUpdateInformation(
    val uri: URI,
    val title: String,
    val events: List<LinkUpdateEvent>
)

data class LinkUpdateEvent(
    val type: String,
    val updateTime: OffsetDateTime,
    val metaInfo: Map<String, String> = mapOf()
)
