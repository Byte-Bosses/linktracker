package ru.bytebosses.scrapper.kafka

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.bytebosses.scrapper.api.common.dto.request.bot.LinkUpdate
import ru.bytebosses.scrapper.configuration.ApplicationConfig

@Service
class ScrapperQueueProducer(
    private val kafkaTemplate: KafkaTemplate<String, LinkUpdate>,
    private val applicationConfig: ApplicationConfig
) {

    fun sendUpdate(update: LinkUpdate) {
        kafkaTemplate.send(applicationConfig.kafkaTopics.linkUpdates, update)
    }
}
