package ru.bytebosses.extension.api.events

interface EventCollectorsRegistry<T> {
    fun getAll(): Map<String, T>

    fun register(type: String, eventCollector: T)

    fun unregister(type: String, eventCollector: T)
}
