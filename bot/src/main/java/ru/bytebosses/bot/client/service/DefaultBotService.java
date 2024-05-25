package ru.bytebosses.bot.client.service;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.bytebosses.bot.api.dto.request.AddLinkRequest;
import ru.bytebosses.bot.api.dto.response.GenericResponse;
import ru.bytebosses.bot.api.dto.response.ListLinksResponse;
import ru.bytebosses.bot.api.httpclient.ScrapperClient;
import ru.bytebosses.bot.models.Chat;
import ru.bytebosses.bot.models.LinkResponse;

@Service
@RequiredArgsConstructor
public class DefaultBotService implements BotService {

    private final ScrapperClient scrapperClient;

    @Override
    public GenericResponse<Void> registerUser(Chat chat) {
        return scrapperClient.registerChat(chat);
    }

    @Override
    @SneakyThrows
    public GenericResponse<LinkResponse> addLinkToDatabase(String url, long chatId) {
        AddLinkRequest addLinkRequest = new AddLinkRequest(new URI(url));
        return scrapperClient.addLinkToTracking(new Chat(chatId), addLinkRequest);
    }

    @Override
    @SneakyThrows
    public GenericResponse<LinkResponse> removeLinkFromDatabase(long linkId, long chatId) {
        return scrapperClient.removeLinkFromTracking(new Chat(chatId), linkId);
    }

    @Override
    public GenericResponse<ListLinksResponse> listLinksFromDatabase(long chatId) {
        return scrapperClient.listLinks(new Chat(chatId));
    }
}
