package ru.bytebosses.extension.stackoverflow.collectors.api

import ru.bytebosses.extension.api.LinkUpdateEvent
import ru.bytebosses.extension.stackoverflow.model.StackoverflowItem

interface StackoverflowEventCollector {
    fun process(info: StackoverflowItem, context: Map<String, String>): Pair<LinkUpdateEvent, Map<String, String>>?
}
