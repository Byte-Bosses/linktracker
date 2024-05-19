package ru.bytebosses.scrapper.api.link.exception

import org.springframework.http.HttpStatus
import ru.bytebosses.scrapper.api.common.exception.ScrapperException
import java.net.URI

class LinkProviderNotFoundException(val link: URI) :
    ScrapperException("Provider for link $link not found", HttpStatus.NOT_FOUND) {
}
