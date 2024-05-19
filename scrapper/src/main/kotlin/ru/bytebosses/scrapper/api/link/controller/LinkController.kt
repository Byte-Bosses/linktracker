package ru.bytebosses.scrapper.api.link.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import ru.bytebosses.scrapper.api.common.dto.request.AddLinkRequest
import ru.bytebosses.scrapper.api.link.dto.LinkResponse
import ru.bytebosses.scrapper.service.LinkService

/**
 * Endpoints for managing links
 */
@RestController
@RequestMapping(value = ["/links/{chat_id}"], consumes = ["application/json"], produces = ["application/json"])
class LinkController(val linkService: LinkService) {

    /**
     * List links for specified chat
     * @throws ru.bytebosses.scrapper.api.chat.exception.ChatIsNotExistException if chat not found
     */
    @GetMapping
    fun listLinks(@PathVariable("chat_id") tgChatId: Long): List<LinkResponse> {
        return linkService.listLinks(tgChatId)
    }

    /**
     * Add new link
     * @throws ru.bytebosses.scrapper.api.chat.exception.ChatIsNotExistException if chat not found
     * @throws ru.bytebosses.scrapper.api.link.exception.LinkAlreadyAddedException if link already added
     */
    @PostMapping
    fun addLink(
        @PathVariable("chat_id") tgChatId: Long,
        @RequestBody addLinkRequest: @Valid AddLinkRequest
    ): LinkResponse {
        return linkService.addLink(addLinkRequest.uri, tgChatId)
    }

    /**
     * Remove link
     * @throws ru.bytebosses.scrapper.api.chat.exception.ChatIsNotExistException if chat not found
     * @throws ru.bytebosses.scrapper.api.link.exception.LinkIsNotExistException if link not found
     */
    @DeleteMapping("{link_id}")
    fun removeLink(
        @PathVariable("chat_id") tgChatId: Long,
        @PathVariable("link_id") linkId: Long
    ): LinkResponse {
        return linkService.removeLink(linkId, tgChatId)
    }
}
