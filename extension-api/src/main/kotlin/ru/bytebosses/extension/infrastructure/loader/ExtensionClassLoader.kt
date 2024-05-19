package ru.bytebosses.extension.infrastructure.loader

import java.net.URL
import java.net.URLClassLoader

/**
 * Load extension classes
 */
class ExtensionClassLoader(url: URL) :
    URLClassLoader("extension", arrayOf(url), ExtensionClassLoader::class.java.classLoader)
