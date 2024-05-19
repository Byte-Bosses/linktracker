package ru.bytebosses.extension.stackoverflow

import com.fasterxml.jackson.core.type.TypeReference
import org.springframework.http.HttpStatusCode
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import reactor.core.publisher.Mono
import ru.bytebosses.extension.api.ExtensionProvider
import ru.bytebosses.extension.api.LinkUpdateEvent
import ru.bytebosses.extension.api.LinkUpdateInformation
import ru.bytebosses.extension.api.configurable.YamlConfigurableInformationProvider
import ru.bytebosses.extension.api.events.ClasspathEventCollectorsRegistry
import ru.bytebosses.extension.api.events.EventCollectorsRegistry
import ru.bytebosses.extension.api.mapper.YamlTextMapper
import ru.bytebosses.extension.stackoverflow.client.StackoverflowApiClient
import ru.bytebosses.extension.stackoverflow.collectors.api.StackoverflowEventCollector
import java.net.URI
import java.time.OffsetDateTime
import java.util.regex.Pattern

@ExtensionProvider(name = "stackoverflow", author = "ardyc", version = "1.0.0")
class StackoverflowInformationProvider : YamlConfigurableInformationProvider<StackoverflowConfiguration>(
    "stackoverflow-provider.yaml",
    StackoverflowConfiguration(System.getenv("STACKOVERFLOW_TOKEN"), BASE_URL)
) {

    private lateinit var client: StackoverflowApiClient
    private val languageMapper = YamlTextMapper.default(StackoverflowInformationProvider::class.java)
    private lateinit var eventRegistry: EventCollectorsRegistry<StackoverflowEventCollector>

    override fun retrieveInformation(
        uri: URI,
        metadata: Map<String, String>,
        lastUpdate: OffsetDateTime
    ): LinkUpdateInformation {
        val matcher = QUESTION_PATTERN.matcher(uri.toString())
        if (!matcher.matches()) throw RuntimeException("Failed to parse uri $uri")
        val questionId = matcher.group(1)
        val info = if (config.token == null) {
            client.getInfo(questionId).items[0]
        } else client.getInfo(questionId, config.token!!).items[0]
        val events = arrayListOf<LinkUpdateEvent>()

        val updateInfo = metadata.toMutableMap()
        eventRegistry.getAll().forEach {
            val event = it.value.process(info, metadata)
            if (event != null) {
                events += event.first.copy(type = languageMapper.map(event.first.type))
                updateInfo += event.second
            }
        }
        return LinkUpdateInformation(uri, events, updateInfo)
    }

    override fun isSupported(uri: URI): Boolean {
        return uri.host == "stackoverflow.com" && QUESTION_PATTERN.matcher(uri.toString()).matches()
    }

    override fun initialize() {
        super.initialize()
        val httpServiceProxyFactory = HttpServiceProxyFactory
            .builderFor(WebClientAdapter.create(createWebClient()))
            .build()
        client = httpServiceProxyFactory.createClient(StackoverflowApiClient::class.java)
        eventRegistry = ClasspathEventCollectorsRegistry
            .create<StackoverflowEventCollector>("ru.bytebosses.extension.stackoverflow.collectors")
            .apply { loadAll() }
    }

    private fun createWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl(config.apiUrl)
            .defaultStatusHandler(
                { _: HttpStatusCode? -> true },
                { _: ClientResponse? -> Mono.empty() })
            .build()
    }

    override fun type(): TypeReference<StackoverflowConfiguration> {
        return object : TypeReference<StackoverflowConfiguration>() {}
    }

    companion object {
        private const val BASE_URL = "https://api.stackexchange.com/2.3/"
        private val QUESTION_PATTERN = Pattern.compile("https://stackoverflow.com/questions/(\\d+).*")
    }
}
