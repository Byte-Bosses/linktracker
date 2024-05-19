package ru.bytebosses.extension.stackoverflow.client

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.service.annotation.GetExchange
import ru.bytebosses.extension.stackoverflow.model.StackoverflowAnswer

interface StackoverflowApiClient {

    @GetExchange("/questions/{questionId}?site=stackoverflow&access_token={token}")
    fun getInfo(@PathVariable questionId: String, @PathVariable token: String): StackoverflowAnswer

    @GetExchange("/questions/{questionId}?site=stackoverflow")
    fun getInfo(@PathVariable questionId: String): StackoverflowAnswer

}
