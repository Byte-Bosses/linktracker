package ru.bytebosses.extension.api

import java.net.URI
import java.time.OffsetDateTime

/**
 * Contains information about link
 * @param uri link
 * @param events list of events
 * @param updateInfo additional information, that can be used to determine the novelty of the link
 */
data class LinkUpdateInformation(
    val uri: URI,
    val events: List<LinkUpdateEvent>,
    val updateInfo: Map<String, String> = mapOf()
)

data class LinkUpdateEvent(
    val type: String,
    val updateTime: OffsetDateTime,
    val metaInfo: Map<String, String> = mapOf()
)
