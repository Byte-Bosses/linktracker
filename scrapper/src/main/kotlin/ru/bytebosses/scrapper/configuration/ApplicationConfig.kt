package ru.bytebosses.scrapper.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
data class ApplicationConfig(
    val scheduler: Scheduler
) {
    data class Scheduler(
        val enable: Boolean,
        val interval: String,
        val forceCheckDelay: String
    )
}
