package ru.bytebosses.scrapper.sender

import ru.bytebosses.scrapper.provider.api.LinkUpdateInformation

interface UpdateSender {
    fun sendUpdate(update: LinkUpdateInformation)
}
