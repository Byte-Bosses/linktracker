package ru.bytebosses.scrapper.api.link.exception

import org.springframework.http.HttpStatus
import ru.bytebosses.scrapper.api.common.exception.ScrapperException

class LinkDoesNotExistException(linkId: Long) :
    ScrapperException("Link with id $linkId does not exists", HttpStatus.NOT_FOUND)
