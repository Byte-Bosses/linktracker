package ru.bytebosses.extension.infrastructure.loader

import org.springframework.util.ClassUtils
import java.net.URL
import java.net.URLClassLoader

/**
 * Load extension classes
 */
class ExtensionClassLoader(url: URL) :
    URLClassLoader("extension", arrayOf(url), ClassUtils.getDefaultClassLoader()) {

}
