package ru.bytebosses.scrapper.scheduler

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.bytebosses.scrapper.api.common.dto.request.bot.LinkUpdate
import ru.bytebosses.scrapper.configuration.ApplicationConfig
import ru.bytebosses.scrapper.model.Link
import ru.bytebosses.scrapper.provider.InformationProvidersRegistry
import ru.bytebosses.scrapper.sender.UpdateSender
import ru.bytebosses.scrapper.service.LinkService

@Service
@EnableScheduling
@ConditionalOnProperty(name = ["app.scheduler.enable"], havingValue = "true")
class LinkUpdatesScheduler(
    private val config: ApplicationConfig,
    private val linkService: LinkService,
    private val updateSender: UpdateSender,
    private val informationProvidersRegistry: InformationProvidersRegistry,
) {

    val log = LoggerFactory.getLogger(LinkUpdatesScheduler::class.java)

    @Scheduled(
        initialDelayString = "#{@'app-ru.bytebosses.scrapper.configuration.ApplicationConfig'.scheduler.initialDelay}",
        fixedRateString = "#{@'app-ru.bytebosses.scrapper.configuration.ApplicationConfig'.scheduler.interval}",
    )
    fun updateLinks() {
        log.info("Updating links...")
        val links =
            linkService.listStaleLinks(
                config.scheduler.limit,
                config.scheduler.forceCheckDelay
            )
        for (link in links) {
            try {
                updateLink(link)
            } catch (e: Exception) {
                log.error("Failed to update link $link", e)
            }
        }
        log.info("Finished updating links")
    }

    fun updateLink(link: Link) {
        log.info("Updating link with id {}", link.id)
        val provider = informationProvidersRegistry
            .findInformationProvider(link.uri)
            .orElseThrow { IllegalStateException("No information provider found for $link") }
        val info = provider.retrieveInformation(link.uri, link.metaInfo, link.lastUpdateTime)
        if (info.events.isNotEmpty()) {
            log.info("{} events happened", info.events.size)
            val tgChats = linkService.getChatIdsForLink(link.id)
            updateSender.sendUpdate(LinkUpdate(info, tgChats))
            val lastUpdateTime = info.events.last().updateTime
            val lastMetaInfo = info.updateInfo
            val updatedLink = link.updated(lastUpdateTime, lastMetaInfo)
            linkService.updateLink(updatedLink)
        } else {
            log.info("No events for this link")
            linkService.updateLink(link.justChecked())
        }
    }
}
