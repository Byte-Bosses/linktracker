package ru.bytebosses.scrapper.sender

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import ru.bytebosses.scrapper.api.common.dto.request.bot.LinkUpdate
import ru.bytebosses.scrapper.integration.bot.BotClient

@Service
@ConditionalOnProperty(name = ["app.sender.type"], havingValue = "http")
class HttpUpdateSender(private val botClient: BotClient) : UpdateSender {
    override fun sendUpdate(update: LinkUpdate) = botClient.handleUpdates(update)
}
