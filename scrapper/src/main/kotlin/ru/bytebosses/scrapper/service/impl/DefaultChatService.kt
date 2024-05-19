package ru.bytebosses.scrapper.service.impl

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import ru.bytebosses.scrapper.api.chat.exception.ChatAlreadyExistException
import ru.bytebosses.scrapper.api.chat.exception.ChatDoesNotExistException
import ru.bytebosses.scrapper.domain.entity.ChatEntity
import ru.bytebosses.scrapper.domain.repository.ChatRepository
import ru.bytebosses.scrapper.domain.repository.LinkRepository
import ru.bytebosses.scrapper.service.TgChatService

@Service
@Transactional
class DefaultChatService(
    private val chatRepository: ChatRepository,
    private val linkRepository: LinkRepository
) : TgChatService {
    override fun register(chatId: Long) {
        if (chatRepository.existsById(chatId))
            throw ChatAlreadyExistException(chatId)
        chatRepository.save(ChatEntity(chatId))
    }

    override fun remove(chatId: Long) {
        if (!chatRepository.existsById(chatId))
            throw ChatDoesNotExistException(chatId)
        val chat = chatRepository.findById(chatId).get()
        val links = ArrayList(chat.links)
        links.forEach {
            chat.removeLink(it)
            if (it.chats.isEmpty())
                linkRepository.delete(it)
        }
        chatRepository.delete(chat)
    }
}
