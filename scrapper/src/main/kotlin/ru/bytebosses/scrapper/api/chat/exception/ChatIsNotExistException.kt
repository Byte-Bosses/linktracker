package ru.bytebosses.scrapper.api.chat.exception

class ChatIsNotExistException(chatId: Long) : RuntimeException("Chat $chatId is not exists")
