package ru.bytebosses.extension.stackoverflow.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime


data class StackoverflowAnswer(
    val items: List<StackoverflowItem>
)

data class StackoverflowItem(
    val title: String,
    @field:JsonProperty("last_activity_date") val lastModified: OffsetDateTime,
    @field:JsonProperty("answer_count") val answersCount: Int,
    val score: Int
)
