package ru.bytebosses.bot.client.service;

import ru.bytebosses.bot.api.dto.response.GenericResponse;
import ru.bytebosses.bot.api.dto.response.ListLinksResponse;
import ru.bytebosses.bot.models.Chat;
import ru.bytebosses.bot.models.LinkResponse;

/**
 * BotService interface provides methods to manage user registration and
 * link management operations such as adding, removing, and listing links.
 */
public interface BotService {

    /**
     * Registers a user based on the provided chat information.
     *
     * @param chat the chat information of the user to be registered.
     * @return a GenericResponse containing a Void result indicating success or failure.
     */
    GenericResponse<Void> registerUser(Chat chat);

    /**
     * Adds a link to the database for a specified chat.
     *
     * @param url the URL to be added.
     * @param chatId the ID of the chat for which the link is to be added.
     * @return a GenericResponse containing a LinkResponse with details of the added link.
     */
    GenericResponse<LinkResponse> addLinkToDatabase(String url, long chatId);

    /**
     * Removes a link from the database for a specified chat.
     *
     * @param linkId the ID of the link to be removed.
     * @param chatId the ID of the chat for which the link is to be removed.
     * @return a GenericResponse containing a LinkResponse with details of the removed link.
     */
    GenericResponse<LinkResponse> removeLinkFromDatabase(long linkId, long chatId);

    /**
     * Lists all links from the database for a specified chat.
     *
     * @param chatId the ID of the chat for which links are to be listed.
     * @return a GenericResponse containing a ListLinksResponse with a list of links.
     */
    GenericResponse<ListLinksResponse> listLinksFromDatabase(long chatId);
}
