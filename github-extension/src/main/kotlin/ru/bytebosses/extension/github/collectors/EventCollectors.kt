package ru.bytebosses.extension.github.collectors

import ru.bytebosses.extension.api.LinkUpdateEvent
import ru.bytebosses.extension.api.events.RegisterEventCollector
import ru.bytebosses.extension.github.collectors.api.GithubEventCollector
import ru.bytebosses.extension.github.model.GithubEventInfo

@RegisterEventCollector("PushEvent")
class PushEventCollector : GithubEventCollector {
    override fun collect(info: GithubEventInfo) = LinkUpdateEvent(
        "github.push_event",
        info.lastModified,
        mapOf(
            "size" to info.payload!!.size!!,
            "repo" to info.repo!!.name,
            "user" to info.actor!!.login
        )
    )
}

@RegisterEventCollector("IssuesEvent")
class IssueEventCollector : GithubEventCollector {
    override fun collect(info: GithubEventInfo) = LinkUpdateEvent(
        "github.issues_event",
        info.lastModified,
        mapOf(
            "title" to info.payload!!.issue!!.title,
            "repo" to info.repo!!.name,
            "user" to info.actor!!.login
        )
    )
}

@RegisterEventCollector("IssueCommentEvent")
class IssueCommentEventCollector : GithubEventCollector {
    override fun collect(info: GithubEventInfo) = LinkUpdateEvent(
        "github.issue_comment_event",
        info.lastModified,
        mapOf(
            "title" to info.payload!!.issue!!.title,
            "repo" to info.repo!!.name,
            "user" to info.actor!!.login
        )
    )
}

@RegisterEventCollector("PullRequestEvent")
class PullRequestEventCollector : GithubEventCollector {
    override fun collect(info: GithubEventInfo) = LinkUpdateEvent(
        "github.pull_request_event",
        info.lastModified,
        mapOf(
            "title" to info.payload!!.pullRequest!!.title,
            "repo" to info.repo!!.name,
            "user" to info.actor!!.login
        )
    )
}

@RegisterEventCollector("PullRequestReviewEvent")
class PullRequestReviewEventCollector : GithubEventCollector {
    override fun collect(info: GithubEventInfo) = LinkUpdateEvent(
        "github.pull_request_review_event",
        info.lastModified,
        mapOf(
            "title" to info.payload!!.pullRequest!!.title,
            "repo" to info.repo!!.name,
            "user" to info.actor!!.login,
            "comment" to (info.payload.review!!.body ?: "")
        )
    )
}

@RegisterEventCollector("CreateEvent")
class CreateEventCollector : GithubEventCollector {
    override fun collect(info: GithubEventInfo) = LinkUpdateEvent(
        "github.create_event",
        info.lastModified,
        mapOf(
            "ref" to info.payload!!.ref!!,
            "ref_type" to info.payload.refType!!,
            "repo" to info.repo!!.name
        )
    )
}
