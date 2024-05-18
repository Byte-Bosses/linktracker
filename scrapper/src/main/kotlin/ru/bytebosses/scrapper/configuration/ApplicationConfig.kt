package ru.bytebosses.scrapper.configuration

import jakarta.validation.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import java.time.Duration

@Validated
@ConfigurationProperties(prefix = "app")
data class ApplicationConfig(
    val scheduler: Scheduler,
    val kafkaTopics: KafkaTopics
) {
    data class Scheduler(
        val enable: Boolean,
        val initialDelay: Duration,
        val interval: Duration,
        val forceCheckDelay: Duration,
        val limit: Int
    )

    data class KafkaTopics(
        @NotEmpty
        val linkUpdates: String
    )
}
