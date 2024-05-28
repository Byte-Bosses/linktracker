package ru.bytebosses.bot.api.service;

import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.bytebosses.bot.api.dto.request.LinkUpdate;
import ru.bytebosses.bot.api.dto.request.LinkUpdateEvent;
import ru.bytebosses.bot.api.dto.request.LinkUpdateInformation;
import ru.bytebosses.bot.printertochat.ChatResponser;

/**
 * LinkUpdatesService class is used for notifying users on updates in tracked links
 **/
@Service
@Log4j2
@RequiredArgsConstructor
public class LinkUpdatesService {
    private final ChatResponser chatResponser;

    /**
     * Notify users with a link update information.
     *
     * @param linkUpdate the link update information to notify users about
     */
    public void notifyUsers(LinkUpdate linkUpdate) {
        log.info("Notifying users...");
        for (long chatId : linkUpdate.tgChatIds()) {
            LinkUpdateInformation information = linkUpdate.linkUpdateInformation();
            for (LinkUpdateEvent event : information.events()) {
                StringBuilder stringMessage = new StringBuilder();
                stringMessage
                    .append("По ссылке ")
                    .append(information.uri())
                    .append(" произошло обновление!\n")
                    .append("Время: ")
                    .append(event.updateTime())
                    .append("\n");
                var eventInfo = event.type();
                for (var info : event.metaInfo().entrySet()) {
                    eventInfo = eventInfo.replace("{" + info.getKey() + "}", info.getValue());
                }
                stringMessage.append("Событие: ")
                    .append(eventInfo);
                SendMessage message = new SendMessage(
                    String.valueOf(chatId),
                    stringMessage.toString()
                );
                chatResponser.sendMessage(message);
            }
        }
    }
}
