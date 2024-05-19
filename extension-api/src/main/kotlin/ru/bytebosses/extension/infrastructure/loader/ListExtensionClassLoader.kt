package ru.bytebosses.extension.infrastructure.loader

import org.springframework.util.ClassUtils

class ListExtensionClassLoader : ClassLoader(ClassUtils.getDefaultClassLoader()) {
    private val children = mutableListOf<ExtensionClassLoader>()
    override fun loadClass(name: String?): Class<*> {
        children.forEach {
            try {
                return it.loadClass(name)
            } catch (e: ClassNotFoundException) {
            }
        }
        throw ClassNotFoundException(name)
    }

    fun addExtensionClassLoader(classLoader: ExtensionClassLoader) {
        children.add(classLoader)
    }
}
