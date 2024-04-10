package ru.bytebosses.scrapper.service

import ru.bytebosses.scrapper.dto.response.LinkResponse
import java.net.URI

interface LinkService {
    fun listLinks(tgChatId: Long): List<LinkResponse>

    fun addLink(link: URI, tgChatId: Long): LinkResponse

    fun removeLink(id: Long, tgChatId: Long): LinkResponse

}
