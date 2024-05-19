package ru.bytebosses.extension.infrastructure.loader

import org.reflections.ReflectionUtils
import org.reflections.Reflections
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.bytebosses.extension.api.ExtensionProvider
import ru.bytebosses.extension.api.InformationProvider
import java.net.URL
import java.nio.file.Path

/**
 * Load extensions from jar and url
 */
class ExtensionLoader(private val classLoader: ListExtensionClassLoader) {
    private val logger: Logger = LoggerFactory.getLogger("ExtensionLoader")
    fun loadExtension(url: URL): InformationProvider {
        logger.info("Loading extension from $url")
        val extensionClassLoader = ExtensionClassLoader(url)
        classLoader.addExtensionClassLoader(extensionClassLoader)
        val reflections = Reflections(
            ConfigurationBuilder().setUrls(
                ClasspathHelper.forClassLoader(
                    extensionClassLoader
                )
            ).addClassLoaders(extensionClassLoader)
        )
        val extensions = reflections.getTypesAnnotatedWith(ExtensionProvider::class.java)
        require(extensions.isNotEmpty()) { "No extension found in $url" }
        val extensionClazz = extensions.first()
        require(ReflectionUtils.getAllSuperTypes(extensionClazz).contains(InformationProvider::class.java)) {
            "Extension $extensionClazz should extend InformationProvider"
        }
        return extensions.first().getConstructor().newInstance() as InformationProvider
    }

    fun loadAllExtensions(
        root: Path,
        extensions: MutableList<InformationProvider> = mutableListOf()
    ): List<InformationProvider> {
        val file = root.toFile()
        if (!file.exists()) {
            return extensions
        }
        if (file.isDirectory) {
            file.listFiles()?.forEach { loadAllExtensions(it.toPath(), extensions) }
            return extensions
        }
        if (file.extension == "jar") {
            extensions.add(loadExtension(file.toURI().toURL()))
        }
        return extensions
    }
}
