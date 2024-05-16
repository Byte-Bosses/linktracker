package ru.bytebosses.scrapper.exception

class ChatAlreadyExistException(val id: Long) : RuntimeException("Chat $id already exists")
