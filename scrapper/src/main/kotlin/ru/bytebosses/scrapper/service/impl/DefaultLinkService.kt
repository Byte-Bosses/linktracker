package ru.bytebosses.scrapper.service.impl

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import ru.bytebosses.scrapper.domain.entity.LinkEntity
import ru.bytebosses.scrapper.domain.repository.ChatRepository
import ru.bytebosses.scrapper.domain.repository.LinkRepository
import ru.bytebosses.scrapper.api.link.dto.LinkResponse
import ru.bytebosses.scrapper.api.chat.exception.ChatIsNotExistException
import ru.bytebosses.scrapper.model.Link
import ru.bytebosses.scrapper.service.LinkService
import java.net.URI
import kotlin.time.Duration

@Service
@Transactional
class DefaultLinkService(
    private val linkRepository: LinkRepository,
    private val chatRepository: ChatRepository
) : LinkService {
    override fun listLinks(tgChatId: Long): List<LinkResponse> {
        val chat = chatRepository.findById(tgChatId).orElseThrow { ChatIsNotExistException(tgChatId) }
        return linkRepository.findAllByChatsContaining(chat)
            .map { LinkResponse(it.id!!, URI.create(it.url!!)) }
    }

    override fun addLink(link: URI, tgChatId: Long): LinkResponse {
        val linkEntity = LinkEntity(url = link.toString())
        val chat = chatRepository.findById(tgChatId).orElseThrow { ChatIsNotExistException(tgChatId) }
        // TODO Add logic with first url check
        chat.addLink(linkEntity)
        linkRepository.save(linkEntity)
        chatRepository.save(chat)
        return LinkResponse(id = linkEntity.id!!, uri = URI.create(linkEntity.url!!))
    }

    override fun removeLink(id: Long, tgChatId: Long): LinkResponse {
        TODO("Not yet implemented")
    }

    override fun listStaleLinks(limit: Int, minTimeSinceLastCheck: Duration): List<Link> {
        TODO("Not yet implemented")
    }

    override fun getChatIdsForLink(id: Long): List<Long> {
        TODO("Not yet implemented")
    }

    override fun updateLink(link: Link) {
        TODO("Not yet implemented")
    }
}
