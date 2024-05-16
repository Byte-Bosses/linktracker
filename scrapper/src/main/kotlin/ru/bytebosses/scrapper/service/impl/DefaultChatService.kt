package ru.bytebosses.scrapper.service.impl

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import ru.bytebosses.scrapper.domain.entity.ChatEntity
import ru.bytebosses.scrapper.domain.repository.ChatRepository
import ru.bytebosses.scrapper.exception.ChatAlreadyExistException
import ru.bytebosses.scrapper.exception.ChatIsNotExistException
import ru.bytebosses.scrapper.service.TgChatService

@Service
@Transactional
class DefaultChatService(private val repository: ChatRepository) : TgChatService {
    override fun register(chatId: Long) {
        if (repository.existsById(chatId))
            throw ChatAlreadyExistException(chatId)
        repository.save(ChatEntity(chatId))
    }

    override fun remove(chatId: Long) {
        if (!repository.existsById(chatId))
            throw ChatIsNotExistException(chatId)
        repository.deleteById(chatId)
    }
}
