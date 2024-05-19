package ru.bytebosses.scrapper.api.common.dto.response

data class ApiErrorResponse(
    val description: String,
    val code: String,
    val exceptionName: String,
    val exceptionMessage: String,
    val stacktrace: List<String>
)
