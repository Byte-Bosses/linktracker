package ru.bytebosses.extension.github

import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.WebClient
import ru.bytebosses.extension.api.ExtensionProvider
import ru.bytebosses.extension.api.LinkUpdateInformation
import ru.bytebosses.extension.api.YamlConfigurableInformationProvider
import java.net.URI

@ExtensionProvider(name = "github", author = "ardyc", version = "1.0.0")
class GithubInformationProvider : YamlConfigurableInformationProvider<GithubProviderConfiguration>(
    "github-provider.yaml",
    GithubProviderConfiguration(System.getenv("GITHUB_TOKEN"), BASE_API_URL)
) {
    private lateinit var client: WebClient

    override fun retrieveInformation(uri: URI, metadata: Map<String, String>): LinkUpdateInformation {
        TODO()
    }

    override fun isSupported(uri: URI): Boolean {
        return uri.host == "github.com"
    }

    override fun initialize() {
        super.initialize()
        client = createWebClient()
        println(config)
    }

    private fun createWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl(config.apiUrl)
            .defaultHeaders { headers: HttpHeaders ->
                if (config.token != null) {
                    headers["Authorization"] = "Bearer ${config.token}"
                }
            }.build()
    }

    companion object {
        private const val BASE_API_URL = "https://api.github.com"
    }

}
