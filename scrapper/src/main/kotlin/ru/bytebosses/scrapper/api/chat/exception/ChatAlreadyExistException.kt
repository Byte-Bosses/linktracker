package ru.bytebosses.scrapper.api.chat.exception

import org.springframework.http.HttpStatus
import ru.bytebosses.scrapper.api.common.exception.ScrapperException

class ChatAlreadyExistException(chatId: Long) :
    ScrapperException("Chat with id $chatId is already exists", HttpStatus.BAD_REQUEST)
