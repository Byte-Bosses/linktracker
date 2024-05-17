package ru.bytebosses.scrapper.api.chat.exception

class ChatAlreadyExistException(val id: Long) : RuntimeException("Chat $id already exists")
