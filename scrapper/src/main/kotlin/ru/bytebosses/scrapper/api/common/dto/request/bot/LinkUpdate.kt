package ru.bytebosses.scrapper.api.common.dto.request.bot

import ru.bytebosses.extension.api.LinkUpdateInformation

data class LinkUpdate(
    val linkUpdateInformation: LinkUpdateInformation,
    val tgChatIds : List<Long>
)
