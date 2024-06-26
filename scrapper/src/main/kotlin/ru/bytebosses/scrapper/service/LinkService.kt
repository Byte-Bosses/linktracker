package ru.bytebosses.scrapper.service

import ru.bytebosses.scrapper.api.link.dto.LinkResponse
import ru.bytebosses.scrapper.model.Link
import java.net.URI
import java.time.Duration


interface LinkService {
    fun listLinks(tgChatId: Long): List<LinkResponse>
    fun addLink(link: URI, tgChatId: Long): LinkResponse
    fun removeLink(id: Long, tgChatId: Long): LinkResponse
    fun listStaleLinks(limit: Int, minTimeSinceLastCheck: Duration): List<Link>
    fun getChatIdsForLink(id: Long): List<Long>
    fun updateLink(link: Link)
}
