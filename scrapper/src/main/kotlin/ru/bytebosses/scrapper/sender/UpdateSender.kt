package ru.bytebosses.scrapper.sender

import ru.bytebosses.scrapper.api.common.dto.request.bot.LinkUpdate

/**
 * Sends updates to specified channels
 */
interface UpdateSender {
    fun sendUpdate(update: LinkUpdate)
}
