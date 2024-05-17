package ru.bytebosses.scrapper.api.chat.controller

import org.springframework.web.bind.annotation.*
import ru.bytebosses.scrapper.service.TgChatService

@RestController
@RequestMapping("/tg-chat/{id}", consumes = ["application/json"], produces = ["application/json"])
class TgChatController(val chatService: TgChatService) {

    @PostMapping
    fun registerChat(@PathVariable(required = true) id: Long) {
        chatService.register(id)
    }

    @DeleteMapping
    fun deleteChat(@PathVariable(required = true) id: Long) {
        chatService.remove(id)
    }

}
