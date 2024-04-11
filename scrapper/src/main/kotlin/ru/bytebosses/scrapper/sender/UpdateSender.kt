package ru.bytebosses.scrapper.sender

import ru.bytebosses.scrapper.dto.request.bot.LinkUpdate

interface UpdateSender {
    fun sendUpdate(update: LinkUpdate)
}
