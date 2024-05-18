package ru.bytebosses.extension.api.configurable

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.slf4j.LoggerFactory
import ru.bytebosses.extension.api.InformationProvider
import java.io.File

abstract class YamlConfigurableInformationProvider<T : Any>(
    private val configFileName: String,
    initialConfiguration: T
) : InformationProvider {
    private val logger = LoggerFactory.getLogger("ExtensionInformationProviderRegistry")
    private val mapper = ObjectMapper(YAMLFactory()).registerModule(KotlinModule.Builder().build())
    var config: T = initialConfiguration

    override fun initialize() {
        saveConfig(configFileName, rewrite = false)
        config = loadConfig(configFileName)
        println(config.javaClass)
    }

    fun loadConfig(path: String): T {
        return try {
            mapper.readValue(File(path), type())
        } catch (e: RuntimeException) {
            logger.error("Failed to load $path: ${e.message}")
            config
        }
    }

    open fun saveConfig(path: String = configFileName, rewrite: Boolean = true) {
        if (rewrite || !File(path).exists()) {
            mapper.writeValue(File(path), config)
        }
    }

    abstract fun type(): TypeReference<T>
}
