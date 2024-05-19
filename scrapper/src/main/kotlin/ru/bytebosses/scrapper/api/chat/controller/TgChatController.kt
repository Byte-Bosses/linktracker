package ru.bytebosses.scrapper.api.chat.controller

import org.springframework.web.bind.annotation.*
import ru.bytebosses.scrapper.service.TgChatService

/**
 * Endpoints for managing Telegram chats
 */
@RestController
@RequestMapping("/tg-chat/{id}", consumes = ["application/json"], produces = ["application/json"])
class TgChatController(val chatService: TgChatService) {

    /**
     * Add telegram chat to observation
     * @throws ru.bytebosses.scrapper.api.chat.exception.ChatAlreadyExistException if chat already registered
     */
    @PostMapping
    fun registerChat(@PathVariable(required = true) id: Long) {
        chatService.register(id)
    }

    /**
     * Remove telegram chat from observation
     * @throws ru.bytebosses.scrapper.api.chat.exception.ChatDoesNotExistException if chat not found
     */
    @DeleteMapping
    fun deleteChat(@PathVariable(required = true) id: Long) {
        chatService.remove(id)
    }

}
