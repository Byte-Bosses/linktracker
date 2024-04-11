package ru.bytebosses.scrapper.service

interface TgChatService {
    fun register(chatId: Long)
    fun remove(chatId: Long)
}
