package ru.bytebosses.scrapper.service

import ru.bytebosses.scrapper.dto.response.LinkResponse
import java.net.URI
import kotlin.time.Duration

interface LinkService {
    fun listLinks(tgChatId: Long): List<LinkResponse>
    fun addLink(link: URI, tgChatId: Long): LinkResponse
    fun removeLink(id: Long, tgChatId: Long): LinkResponse
    fun listStaleLinks(limit: Int, minTimeSinceLastCheck: Duration)
    fun getChatIdsForLink(id: Long): List<Long>
}
