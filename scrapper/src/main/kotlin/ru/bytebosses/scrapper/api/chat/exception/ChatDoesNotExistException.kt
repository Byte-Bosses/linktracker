package ru.bytebosses.scrapper.api.chat.exception

import org.springframework.http.HttpStatus
import ru.bytebosses.scrapper.api.common.exception.ScrapperException

class ChatDoesNotExistException(chatId: Long) :
    ScrapperException("Chat with id $chatId does not exists", HttpStatus.NOT_FOUND)
