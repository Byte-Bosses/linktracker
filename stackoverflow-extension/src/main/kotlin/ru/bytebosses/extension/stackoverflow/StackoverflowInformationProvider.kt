package ru.bytebosses.extension.stackoverflow

import com.fasterxml.jackson.core.type.TypeReference
import ru.bytebosses.extension.api.ExtensionProvider
import ru.bytebosses.extension.api.LinkUpdateInformation
import ru.bytebosses.extension.api.configurable.YamlConfigurableInformationProvider
import java.net.URI
import java.time.OffsetDateTime
import java.util.regex.Pattern

@ExtensionProvider(name = "stackoverflow", author = "ardyc", version = "1.0.0")
class StackoverflowInformationProvider : YamlConfigurableInformationProvider<StackoverflowConfiguration>(
    "stackoverflow-provider.yml",
    StackoverflowConfiguration(System.getenv("STACKOVERFLOW_TOKEN"), BASE_URL)
) {
    override fun retrieveInformation(
        uri: URI,
        metadata: Map<String, String>,
        lastUpdate: OffsetDateTime
    ): LinkUpdateInformation {
        TODO("Not yet implemented")
    }

    override fun isSupported(uri: URI): Boolean {
        return uri.host == "stackoverflow.com" && QUESTION_PATTERN.matcher(uri.toString()).matches()
    }

    override fun type(): TypeReference<StackoverflowConfiguration> {
        return object : TypeReference<StackoverflowConfiguration>() {}
    }

    companion object {
        private const val BASE_URL = "https://api.stackexchange.com/2.3/"
        private val QUESTION_PATTERN = Pattern.compile("https://stackoverflow.com/questions/(\\d+).*")
    }
}
