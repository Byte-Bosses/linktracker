package ru.bytebosses.bot.api.endpoints.kafkaConsumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.stereotype.Component;
import ru.bytebosses.bot.api.dto.request.LinkUpdate;
import ru.bytebosses.bot.api.service.LinkUpdatesService;
/**
 * KafkaUpdatesListener class is used for asynchronous receiving of updates on links from scrapper module
 * **/
@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaUpdatesListener {
    private final LinkUpdatesService service;

    @KafkaListener(topics = "linkUpdate", groupId = "scrapper")
    @RetryableTopic(attempts = "1", dltStrategy = DltStrategy.FAIL_ON_ERROR, dltTopicSuffix = "_dlq")
    public void updateLinks(LinkUpdate linkUpdate) {
        service.notifyUsers(linkUpdate);
    }

    @DltHandler
    public void handleError(LinkUpdate linkUpdate) {
        log.info("Ошибка обработки сообщения. Отправлено в очередь плохих сообщений");
    }
}
