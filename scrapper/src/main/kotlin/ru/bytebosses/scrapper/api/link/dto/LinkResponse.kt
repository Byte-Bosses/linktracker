package ru.bytebosses.scrapper.api.link.dto

import java.net.URI

data class LinkResponse(
    var id: Long,
    var uri: URI
)
