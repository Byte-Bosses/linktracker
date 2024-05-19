package ru.bytebosses.extension.github

import com.fasterxml.jackson.core.type.TypeReference
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import reactor.core.publisher.Mono
import ru.bytebosses.extension.api.ExtensionProvider
import ru.bytebosses.extension.api.LinkUpdateInformation
import ru.bytebosses.extension.api.configurable.YamlConfigurableInformationProvider
import ru.bytebosses.extension.api.events.ClasspathEventCollectorsRegistry
import ru.bytebosses.extension.api.events.EventCollectorsRegistry
import ru.bytebosses.extension.api.mapper.YamlTextMapper
import ru.bytebosses.extension.github.client.GithubApiClient
import ru.bytebosses.extension.github.collectors.api.GithubEventCollector
import ru.bytebosses.extension.github.model.GithubEventInfo
import java.net.URI
import java.time.OffsetDateTime
import java.util.regex.Pattern

@ExtensionProvider(name = "github", author = "ardyc", version = "1.0.0")
class GithubInformationProvider : YamlConfigurableInformationProvider<GithubProviderConfiguration>(
    "github-provider.yaml",
    GithubProviderConfiguration(System.getenv("GITHUB_TOKEN"), BASE_API_URL)
) {
    private lateinit var client: GithubApiClient
    private lateinit var eventRegistry: EventCollectorsRegistry<GithubEventCollector>
    private val languageMapper = YamlTextMapper.default(GithubInformationProvider::class.java)

    override fun retrieveInformation(
        uri: URI,
        metadata: Map<String, String>,
        lastUpdate: OffsetDateTime
    ): LinkUpdateInformation {
        try {
            val events = collectEvents(uri, lastUpdate)
            return LinkUpdateInformation(
                uri,
                events.mapNotNull { eventRegistry.getAll()[it.type]?.collect(it) }
                    .map { it.copy(type = languageMapper.map(it.type)) }
                    .reversed(),
                metadata
            )
        } catch (e: Exception) {
            throw RuntimeException("Failed to retrieve information from $uri", e)
        }
    }

    private fun collectEvents(
        uri: URI,
        lastUpdate: OffsetDateTime,
        page: Int = 1,
        collected: MutableList<GithubEventInfo> = arrayListOf()
    ): List<GithubEventInfo> {
        val user = uri.path.split('/')[1]
        val repo = uri.path.split('/')[2]
        val events = client.getEvents(user, repo, PER_PAGE_COUNT, page)
        var finished = false
        events.forEach {
            if (it.lastModified.isBefore(lastUpdate) || it.lastModified.isEqual(lastUpdate)) {
                finished = true
                return@forEach
            }
            collected += it
        }
        if (!finished) {
            return collectEvents(uri, lastUpdate, page + 1, collected)
        }
        return collected
    }

    override fun isSupported(uri: URI): Boolean {
        return uri.host == "github.com" && GITHUB_LINK_PATTERN.matcher(uri.toString()).matches()
    }

    override fun initialize() {
        super.initialize()
        val httpServiceProxyFactory = HttpServiceProxyFactory
            .builderFor(WebClientAdapter.create(createWebClient()))
            .build()
        client = httpServiceProxyFactory.createClient(GithubApiClient::class.java)
        eventRegistry = ClasspathEventCollectorsRegistry
            .create<GithubEventCollector>("ru.bytebosses.extension.github.collectors")
            .apply { loadAll() }
    }


    private fun createWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl(config.apiUrl)
            .defaultStatusHandler(
                { _: HttpStatusCode? -> true },
                { _: ClientResponse? -> Mono.empty() })
            .defaultHeaders { headers: HttpHeaders ->
                if (config.token != null) {
                    headers["Authorization"] = "Bearer ${config.token}"
                }
            }.build()
    }

    override fun type(): TypeReference<GithubProviderConfiguration> {
        return object : TypeReference<GithubProviderConfiguration>() {}
    }

    companion object {
        private const val BASE_API_URL = "https://api.github.com"
        private const val PER_PAGE_COUNT = 5
        private val GITHUB_LINK_PATTERN = Pattern.compile("https://github.com/(.+)/(.+)")
    }

}
