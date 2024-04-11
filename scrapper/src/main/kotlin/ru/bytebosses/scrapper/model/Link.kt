package ru.bytebosses.scrapper.model

import java.net.URI
import java.time.OffsetDateTime

data class Link(
    val id: Long,
    val uri: URI,
    var lastUpdateTime: OffsetDateTime,
    var lastCheckTime: OffsetDateTime,
    var metaInfo: Map<String, String>
)
