package ru.bytebosses.extension.api

abstract class ConfigurableInformationProvider(protected val config: MutableMap<String, String> = hashMapOf()) :
    InformationProvider {
    open fun configure(key: String, value: String) {
        config[key] = value
    }

    fun getConfiguration(key: String): String {
        return config[key] ?: throw IllegalArgumentException("key $key not found")
    }
}
