package ru.bytebosses.scrapper.integration.bot

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.PostExchange
import ru.bytebosses.scrapper.api.common.dto.request.bot.LinkUpdate

interface BotClient {
    @PostExchange("/updates")
    fun handleUpdates(@RequestBody linkUpdate: @Valid LinkUpdate)
}
