package ru.bytebosses.scrapper.api.chat.exception

import org.springframework.http.HttpStatus
import ru.bytebosses.scrapper.api.common.exception.ScrapperException

class ChatIsNotExistException(chatId: Long) :
    ScrapperException("Chat with id $chatId is not exists", HttpStatus.NOT_FOUND)
