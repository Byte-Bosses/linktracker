package ru.bytebosses.extension.api

/**
 * Used to provide information about extension
 * Class, marked with `@ExtensionProvider` should be loaded, if it implements `InformationProvider`
 * and loaded in scrapper context
 */
annotation class ExtensionProvider(
    val name: String,
    val author: String = "unknown",
    val version: String = "1.0.0"
)
