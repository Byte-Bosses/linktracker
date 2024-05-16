package ru.bytebosses.extension.infra.loader

import java.net.URL
import java.net.URLClassLoader

class ExtensionClassLoader(url: URL) :
    URLClassLoader("extension", arrayOf(url), ExtensionClassLoader::class.java.classLoader)
