package ru.bytebosses.scrapper.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import ru.bytebosses.scrapper.dto.request.AddLinkRequest
import ru.bytebosses.scrapper.dto.response.LinkResponse
import ru.bytebosses.scrapper.service.LinkService

@RestController
@RequestMapping(value = ["/links/{chat_id}"], consumes = ["application/json"], produces = ["application/json"])
class LinkController(val linkService: LinkService) {

    @GetMapping
    fun listLinks(@PathVariable("chat_id") tgChatId: Long): List<LinkResponse> {
        return linkService.listLinks(tgChatId)
    }

    @PostMapping
    fun addLink(
        @PathVariable("chat_id") tgChatId: Long,
        @RequestBody addLinkRequest: @Valid AddLinkRequest
    ): LinkResponse {
        return linkService.addLink(addLinkRequest.uri, tgChatId)
    }

    @DeleteMapping("{link_id}")
    fun removeLink(
        @PathVariable("chat_id") tgChatId: Long,
        @PathVariable("link_id") linkId: Long
    ): LinkResponse {
        return linkService.removeLink(linkId, tgChatId)
    }
}
