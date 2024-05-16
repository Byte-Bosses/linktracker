package ru.bytebosses.scrapper.exception

class ChatIsNotExistException(chatId: Long) : RuntimeException("Chat $chatId is not exists")
