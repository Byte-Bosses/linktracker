package ru.bytebosses.scrapper.dto.response

import java.net.URI

data class LinkResponse(
    var id: Long,
    var uri: URI
)
