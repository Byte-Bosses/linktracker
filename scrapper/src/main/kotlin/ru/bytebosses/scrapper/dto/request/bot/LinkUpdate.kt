package ru.bytebosses.scrapper.dto.request.bot

import ru.bytebosses.scrapper.provider.api.LinkUpdateInformation

data class LinkUpdate(
    val linkUpdateInformation: LinkUpdateInformation,
    val tgChatIds : List<Long>
)
