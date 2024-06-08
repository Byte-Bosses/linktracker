package ru.bytebosses.scrapper.service.impl

import jakarta.transaction.Transactional
import org.springframework.data.domain.Limit
import org.springframework.stereotype.Service
import ru.bytebosses.scrapper.api.chat.exception.ChatDoesNotExistException
import ru.bytebosses.scrapper.api.link.dto.LinkResponse
import ru.bytebosses.scrapper.api.link.exception.LinkAlreadyAddedException
import ru.bytebosses.scrapper.api.link.exception.LinkDoesNotExistException
import ru.bytebosses.scrapper.api.link.exception.LinkProviderNotFoundException
import ru.bytebosses.scrapper.domain.entity.LinkEntity
import ru.bytebosses.scrapper.domain.repository.ChatRepository
import ru.bytebosses.scrapper.domain.repository.LinkRepository
import ru.bytebosses.scrapper.model.Link
import ru.bytebosses.scrapper.provider.InformationProvidersRegistry
import ru.bytebosses.scrapper.service.LinkService
import java.net.URI
import java.time.Duration
import java.time.OffsetDateTime


@Service
@Transactional
class DefaultLinkService(
    private val linkRepository: LinkRepository,
    private val chatRepository: ChatRepository,
    private val informationProviders: InformationProvidersRegistry
) : LinkService {
    override fun listLinks(tgChatId: Long): List<LinkResponse> {
        val chat = chatRepository.findById(tgChatId).orElseThrow { ChatDoesNotExistException(tgChatId) }
        return chat.links
            .map { LinkResponse(it.id!!, URI.create(it.url!!)) }
    }

    override fun addLink(link: URI, tgChatId: Long): LinkResponse {
        val chat = chatRepository.findById(tgChatId).orElseThrow { ChatDoesNotExistException(tgChatId) }
        val optionalLink = linkRepository.findByUrl(link.toString())
        if (optionalLink.isPresent) {
            val linkEntity = optionalLink.get()
            if (linkEntity.chats.contains(chat)) throw LinkAlreadyAddedException(linkEntity.id!!)
            chat.addLink(linkEntity)
            return LinkResponse(linkEntity.id!!, link)
        }
        val provider =
            informationProviders.findInformationProvider(link).orElseThrow { LinkProviderNotFoundException(link) }
        val linkInformation = provider.retrieveInformation(link)
        var lastModified = OffsetDateTime.now()
        if (linkInformation.events.isNotEmpty()) {
            lastModified = linkInformation.events.first().updateTime
        }
        val linkEntity = LinkEntity(
            url = link.toString(),
            lastUpdatedAt = lastModified,
            lastCheckedAt = OffsetDateTime.now(),
            metaInfo = linkInformation.updateInfo
        )
        chat.addLink(linkEntity)
        linkRepository.save(linkEntity)
        chatRepository.save(chat)
        return LinkResponse(id = linkEntity.id!!, url = URI.create(linkEntity.url!!))
    }

    override fun removeLink(id: Long, tgChatId: Long): LinkResponse {
        val chat = chatRepository.findById(tgChatId).orElseThrow { LinkDoesNotExistException(id) }
        val link = linkRepository.findById(id).orElseThrow()
        chat.removeLink(link)
        if (link.chats.isEmpty()) {
            linkRepository.delete(link)
        }
        return LinkResponse(id, URI.create(link.url!!))
    }

    override fun listStaleLinks(limit: Int, minTimeSinceLastCheck: Duration): List<Link> {
        return linkRepository.findAllByLastCheckedAtBeforeOrderByLastCheckedAt(
            OffsetDateTime.now().minus(minTimeSinceLastCheck), Limit.of(limit)
        ).map { it.toModel() }
    }

    override fun getChatIdsForLink(id: Long): List<Long> {
        val link = linkRepository.findById(id).orElseThrow()
        return link.chats.map { chat -> chat.id!! }
    }

    override fun updateLink(link: Link) {
        val linkEntity = linkRepository.findById(link.id).orElseThrow()
        linkEntity.apply {
            url = link.uri.toString()
            lastUpdatedAt = link.lastUpdateTime
            lastCheckedAt = link.lastCheckTime
            metaInfo = link.metaInfo
        }
    }
}
