package ru.bytebosses.scrapper.sender

import ru.bytebosses.scrapper.api.common.dto.request.bot.LinkUpdate

interface UpdateSender {
    fun sendUpdate(update: LinkUpdate)
}
