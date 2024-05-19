package ru.bytebosses.extension.api.configurable

import ru.bytebosses.extension.api.InformationProvider

abstract class ConfigurableInformationProvider(private val config: MutableMap<String, String> = hashMapOf()) :
    InformationProvider {
    open fun configure(key: String, value: String) {
        config[key] = value
    }

    fun getConfiguration(key: String): String {
        return config[key] ?: throw IllegalArgumentException("key $key not found")
    }
}
