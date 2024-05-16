package ru.bytebosses.extension.api

import org.yaml.snakeyaml.Yaml
import java.io.File

abstract class YamlConfigurableInformationProvider<T : Any>(
    private val configFileName: String,
    initialConfiguration: T
) : InformationProvider {
    private val yaml = Yaml()
    var config: T = initialConfiguration

    override fun initialize() {
        saveConfig(configFileName, rewrite = false)
        config = loadConfig(configFileName)
    }

    private fun loadConfig(path: String): T {
        return yaml.load(File(path).inputStream())
    }

    open fun saveConfig(path: String, rewrite: Boolean = true) {
        if (rewrite || !File(path).exists()) {
            yaml.dump(config, File(path).writer())
        }
    }
}
