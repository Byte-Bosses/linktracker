package ru.bytebosses.scrapper.sender

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import ru.bytebosses.scrapper.api.common.dto.request.bot.LinkUpdate
import ru.bytebosses.scrapper.kafka.ScrapperQueueProducer

@Service
@ConditionalOnProperty(name = ["app.sender.type"], havingValue = "kafka")
class KafkaUpdateSender(private val scrapperQueueProducer: ScrapperQueueProducer) : UpdateSender {

    override fun sendUpdate(update: LinkUpdate) = scrapperQueueProducer.sendUpdate(update)
}
