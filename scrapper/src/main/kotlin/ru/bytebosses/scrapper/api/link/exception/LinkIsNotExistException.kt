package ru.bytebosses.scrapper.api.link.exception

import org.springframework.http.HttpStatus
import ru.bytebosses.scrapper.api.common.exception.ScrapperException

class LinkIsNotExistException(linkId: Long) :
    ScrapperException("Link with id $linkId is not exists", HttpStatus.NOT_FOUND)
