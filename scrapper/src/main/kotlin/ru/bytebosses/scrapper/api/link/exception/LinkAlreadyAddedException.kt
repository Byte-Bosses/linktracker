package ru.bytebosses.scrapper.api.link.exception

import org.springframework.http.HttpStatus
import ru.bytebosses.scrapper.api.common.exception.ScrapperException

class LinkAlreadyAddedException(linkId: Long) :
    ScrapperException("Link with id $linkId is already added", HttpStatus.BAD_REQUEST)
