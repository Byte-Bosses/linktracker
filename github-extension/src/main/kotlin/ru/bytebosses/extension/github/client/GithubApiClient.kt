package ru.bytebosses.extension.github.client

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.service.annotation.GetExchange
import ru.bytebosses.extension.github.model.GithubEventInfo

interface GithubApiClient {
    @GetExchange("/repos/{user}/{repo}/events?per_page={count}&page={page}")
    fun getEvents(
        @PathVariable user: String,
        @PathVariable repo: String,
        @PathVariable count: Int,
        @PathVariable page: Int
    ): List<GithubEventInfo>

}
