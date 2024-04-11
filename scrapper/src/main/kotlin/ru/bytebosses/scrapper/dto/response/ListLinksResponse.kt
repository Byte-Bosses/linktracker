package ru.bytebosses.scrapper.dto.response

data class ListLinksResponse (
    val links: List<LinkResponse>,
    val size: Int
)
