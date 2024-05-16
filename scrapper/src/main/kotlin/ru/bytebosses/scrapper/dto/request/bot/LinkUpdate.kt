package ru.bytebosses.scrapper.dto.request.bot

import ru.bytebosses.extension.api.LinkUpdateInformation

data class LinkUpdate(
    val linkUpdateInformation: LinkUpdateInformation,
    val tgChatIds : List<Long>
)
