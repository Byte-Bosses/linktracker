package ru.bytebosses.scrapper.api.common.exception

import org.springframework.http.HttpStatusCode

open class ScrapperException(override val message: String, val code: HttpStatusCode) : RuntimeException(
    message
)
