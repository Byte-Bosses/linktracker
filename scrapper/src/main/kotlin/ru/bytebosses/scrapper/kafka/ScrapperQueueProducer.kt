package ru.bytebosses.scrapper.kafka

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.bytebosses.scrapper.configuration.ApplicationConfig
import ru.bytebosses.scrapper.dto.request.bot.LinkUpdate

@Service
class ScrapperQueueProducer(
    private val kafkaTemplate: KafkaTemplate<String, LinkUpdate>,
    private val applicationConfig: ApplicationConfig
) {

    fun sendUpdate(update: LinkUpdate) {
        kafkaTemplate.send(applicationConfig.kafkaTopics.linkUpdates, update)
    }
}
