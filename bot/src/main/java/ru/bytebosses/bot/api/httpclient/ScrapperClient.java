package ru.bytebosses.bot.api.httpclient;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.bytebosses.bot.api.dto.request.AddLinkRequest;
import ru.bytebosses.bot.api.dto.response.ApiErrorResponse;
import ru.bytebosses.bot.api.dto.response.GenericResponse;
import ru.bytebosses.bot.api.dto.response.ListLinksResponse;
import ru.bytebosses.bot.models.Chat;
import ru.bytebosses.bot.models.LinkResponse;
import ru.bytebosses.bot.models.RetryRule;
import ru.bytebosses.bot.util.RetryFactory;

/**
 * ScrapperClient class is used for sending requests by HTTP protocol to scrapper module
 **/
@Log4j2 public class ScrapperClient {
    private final static String BASE_PORT = "8080";
    private final static String BASE_URL = "http://localhost:" + BASE_PORT;
    private final static String PATH_FOR_CHAT_CONTROLLER = "/tg-chat/";
    private final static String PATH_FOR_LINKS_CONTROLLER = "/links/";
    private final static ApiErrorResponse EXHAUSTED_RETRY = new ApiErrorResponse(
        "по каким-то причинам сервер временно недоступен, повторите запрос позже",
        HttpStatus.REQUEST_TIMEOUT.toString(),
        "",
        "",
        List.of()
    );
    private final WebClient webClient;

    public ScrapperClient(RetryRule rule) {
        this.webClient = WebClient.builder().filter(RetryFactory.createFilter(rule))
            .defaultStatusHandler(httpStatusCode -> true, clientResponse -> Mono.empty()).baseUrl(BASE_URL).build();
    }

    public ScrapperClient(String baseUrl, RetryRule rule) {
        this.webClient = WebClient.builder().filter(RetryFactory.createFilter(rule))
            .defaultStatusHandler(httpStatusCode -> true, clientResponse -> Mono.empty()).baseUrl(baseUrl).build();
    }

    /**
     * Register a chat by sending a POST request to the chat controller.
     *
     * @param chat the chat object to be registered
     * @return a GenericResponse object representing the result of the registration.
     *     In this case response filed is always null, but errorResponse field can be either empty or not.
     */
    public GenericResponse<Void> registerChat(Chat chat) {
        var clientResponse =
            webClient.method(HttpMethod.POST)
                .uri(PATH_FOR_CHAT_CONTROLLER + chat.id())
                .contentType(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(Void.class);
                    }
                    return response.bodyToMono(ApiErrorResponse.class);
                })
                .onErrorReturn(throwable -> throwable instanceof IllegalStateException, EXHAUSTED_RETRY)
                .block();
        if (clientResponse == null) {
            return new GenericResponse<>(null, null);
        }
        return new GenericResponse<>(null, (ApiErrorResponse) clientResponse);
    }

    /**
     * Deletes a chat by sending a DELETE request to the server.
     *
     * @param chat the chat object to be deleted
     * @return a GenericResponse object containing the result of the deletion.
     *     In this case response filed is always null, but errorResponse field can be either empty or not.
     */
    public GenericResponse<Void> deleteChat(Chat chat) {
        var clientResponse =
            webClient.method(HttpMethod.DELETE)
                .uri(PATH_FOR_CHAT_CONTROLLER + chat.id())
                .contentType(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(Void.class);
                    }
                    return response.bodyToMono(ApiErrorResponse.class);
                })
                .onErrorReturn(throwable -> throwable instanceof IllegalStateException, EXHAUSTED_RETRY)
                .block();
        if (clientResponse == null) {
            return new GenericResponse<>(null, null);
        }
        return new GenericResponse<>(null, (ApiErrorResponse) clientResponse);
    }

    /**
     * Retrieves a list of links for a given chat.
     *
     * @param chat the chat for which to list links
     * @return a GenericResponse object containing either a list of links in case of success or
     *     an ApiErrorResponse in case of error
     */
    public GenericResponse<ListLinksResponse> listLinks(Chat chat) {
        var clientResponse =
            webClient.method(HttpMethod.GET)
                .uri(PATH_FOR_LINKS_CONTROLLER + chat.id())
                .contentType(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(LinkResponse[].class);
                    }
                    return response.bodyToMono(ApiErrorResponse.class);
                })
                .onErrorReturn(throwable -> throwable instanceof IllegalStateException, EXHAUSTED_RETRY)
                .block();
        if (clientResponse instanceof LinkResponse[]) {
            return new GenericResponse<>(new ListLinksResponse(List.of((LinkResponse[]) clientResponse)), null);
        }
        return new GenericResponse<>(null, (ApiErrorResponse) clientResponse);
    }

    /**
     * Adds a link to tracking.
     *
     * @param chat           the Chat object related to the link
     * @param addLinkRequest the request object containing the link to add
     * @return a GenericResponse object with the corresponding response.
     *     In case of success the response field contains LinkResponse object with id and url of link and
     *     errorResponse is null. In case of error the response filed is null and errorResponse field contains
     *     ApiErrorResponse object.
     */
    public GenericResponse<LinkResponse> addLinkToTracking(Chat chat, AddLinkRequest addLinkRequest) {
        var clientResponse =
            webClient.method(HttpMethod.POST)
                .uri(PATH_FOR_LINKS_CONTROLLER + chat.id())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(addLinkRequest)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(LinkResponse.class);
                    }
                    return response.bodyToMono(ApiErrorResponse.class);
                })
                .onErrorReturn(throwable -> throwable instanceof IllegalStateException, EXHAUSTED_RETRY)
                .block();
        if (clientResponse instanceof LinkResponse) {
            return new GenericResponse<>((LinkResponse) clientResponse, null);
        }
        return new GenericResponse<>(null, (ApiErrorResponse) clientResponse);
    }

    /**
     * Removes a link from tracking.
     *
     * @param chat the chat object
     * @param id   the id of the link to remove
     * @return a GenericResponse object with the corresponding response.
     *     In case of success the response field contains LinkResponse object with id and url of link
     *     and errorResponse is null. In case of error the response filed is null and errorResponse field contains
     *     ApiErrorResponse object.
     */
    public GenericResponse<LinkResponse> removeLinkFromTracking(
        Chat chat, long id
    ) {
        var clientResponse = webClient.method(HttpMethod.DELETE)
            .uri(PATH_FOR_LINKS_CONTROLLER + chat.id() + "/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .exchangeToMono(response -> {
                if (response.statusCode().is2xxSuccessful()) {
                    return response.bodyToMono(LinkResponse.class);
                }
                return response.bodyToMono(ApiErrorResponse.class);
            })
            .onErrorReturn(throwable -> throwable instanceof IllegalStateException, EXHAUSTED_RETRY)
            .block();
        if (clientResponse instanceof LinkResponse) {
            return new GenericResponse<>((LinkResponse) clientResponse, null);
        }
        return new GenericResponse<>(null, (ApiErrorResponse) clientResponse);
    }
}
