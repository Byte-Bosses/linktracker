package ru.bytebosses.extension.github.collectors.api

import ru.bytebosses.extension.api.LinkUpdateEvent
import ru.bytebosses.extension.github.model.GithubEventInfo

interface GithubEventCollector {
    fun collect(info: GithubEventInfo): LinkUpdateEvent
}
