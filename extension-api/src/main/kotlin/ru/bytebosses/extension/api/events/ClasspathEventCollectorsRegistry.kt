package ru.bytebosses.extension.api.events

import org.reflections.Reflections

open class ClasspathEventCollectorsRegistry<T>(private val classpath: String) : EventCollectorsRegistry<T> {
    private val eventCollectors = mutableMapOf<String, T>()

    override fun getAll(): Map<String, T> = eventCollectors

    override fun register(type: String, eventCollector: T) {
        eventCollectors[type] = eventCollector
    }

    override fun unregister(type: String, eventCollector: T) {
        eventCollectors.remove(type)
    }

    fun loadAll() {
        Reflections(classpath)
            .getTypesAnnotatedWith(RegisterEventCollector::class.java)
            .forEach {
                val collector = it.getConstructor().newInstance() as T
                register(it.getAnnotation(RegisterEventCollector::class.java).type, collector)
            }
    }

    companion object {
        fun <T> create(classpath: String): ClasspathEventCollectorsRegistry<T> {
            return ClasspathEventCollectorsRegistry(classpath)
        }
    }
}
