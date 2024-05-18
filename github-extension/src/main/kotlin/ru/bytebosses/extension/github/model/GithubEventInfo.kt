package ru.bytebosses.extension.github.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime


data class GithubEventInfo(
    @field:JsonProperty("created_at") val lastModified: OffsetDateTime,
    val type: String?,
    val repo: RepositoryInfo?,
    val payload: EventPayload?,
    val actor: Actor?
) {
    data class RepositoryInfo(val name: String)

    data class EventPayload(
        val size: String?,
        val ref: String?,
        val issue: IssueCommentEventPayload?,
        @field:JsonProperty("pull_request") val pullRequest: PullRequestEventPayload?,
        @field:JsonProperty("ref_type") val refType: String?
    )

    data class IssueCommentEventPayload(
        val title: String
    )

    data class PullRequestEventPayload(
        val title: String
    )

    data class Actor(
        val login: String
    )
}
