package ru.bytebosses.extension.github

import org.springframework.boot.jackson.JsonMixin

data class GithubProviderConfiguration(
    var token: String?,
    var apiUrl: String
)
