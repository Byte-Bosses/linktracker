package ru.bytebosses.extension.stackoverflow.collectors

import ru.bytebosses.extension.api.LinkUpdateEvent
import ru.bytebosses.extension.api.events.RegisterEventCollector
import ru.bytebosses.extension.stackoverflow.collectors.api.StackoverflowEventCollector
import ru.bytebosses.extension.stackoverflow.model.StackoverflowItem
import java.time.OffsetDateTime

@RegisterEventCollector("AnswerEvent")
class StackoverflowAnswerEventCollector : StackoverflowEventCollector {
    override fun process(
        info: StackoverflowItem,
        context: Map<String, String>
    ): Pair<LinkUpdateEvent, Map<String, String>>? {
        val currentCount = context["answer_count"]?.toInt() ?: 0
        if (info.answersCount > currentCount) {
            val newContext = context.toMutableMap()
            newContext["answer_count"] = info.answersCount.toString()
            return LinkUpdateEvent(
                "stackoverflow.answer_event",
                OffsetDateTime.now(),
                mapOf(
                    "title" to info.title,
                    "count" to info.answersCount.toString()
                )
            ) to newContext
        }
        return null
    }
}


@RegisterEventCollector("ScoreEvent")
class StackoverflowScoreEventCollector : StackoverflowEventCollector {
    override fun process(
        info: StackoverflowItem,
        context: Map<String, String>
    ): Pair<LinkUpdateEvent, Map<String, String>>? {
        val currentScore = context["score"]?.toInt() ?: 0
        if (info.score > currentScore) {
            val newContext = context.toMutableMap()
            newContext["score"] = info.score.toString()
            return LinkUpdateEvent(
                "stackoverflow.score_event",
                OffsetDateTime.now(),
                mapOf(
                    "title" to info.title,
                    "score" to info.score.toString()
                )
            ) to newContext
        }
        return null
    }
}
