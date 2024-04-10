package ru.bytebosses.scrapper

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import ru.bytebosses.scrapper.configuration.ApplicationConfig

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig::class)
class ScrapperApplication

fun main(args: Array<String>) {
    runApplication<ScrapperApplication>(*args)
}
