package ru.bytebosses.scrapper.model

import java.net.URI
import java.time.OffsetDateTime

data class Link(
    val id: Long,
    val uri: URI,
    var lastUpdateTime: OffsetDateTime,
    var lastCheckTime: OffsetDateTime,
    var metaInfo: Map<String, String>
) {

    fun updated(lastUpdateTime: OffsetDateTime, metaInfo: Map<String, String>): Link {
        return justChecked().apply {
            this.lastUpdateTime = lastUpdateTime
            this.metaInfo = metaInfo
        }
    }

    fun justChecked(): Link = apply {
        lastCheckTime = OffsetDateTime.now()
    }
}
