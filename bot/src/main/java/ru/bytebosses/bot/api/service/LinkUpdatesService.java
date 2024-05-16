package ru.bytebosses.bot.api.service;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.bytebosses.bot.api.dto.request.LinkUpdate;
import ru.bytebosses.bot.api.dto.request.LinkUpdateEvent;
import ru.bytebosses.bot.api.dto.request.LinkUpdateInformation;
import ru.bytebosses.bot.printerToChat.ChatResponser;

/**
 * LinkUpdatesService class is used for notifying users on updates in tracked links
 * **/
@Service
@Log4j2
@RequiredArgsConstructor
public class LinkUpdatesService {
    private final ChatResponser chatResponser;

    /**
     * Notify users with a link update information.
     *
     * @param  linkUpdate	the link update information to notify users about
     */
    public void notifyUsers(LinkUpdate linkUpdate) {
        log.info("Notifying users...");
        for (long chatId : linkUpdate.chatIds()) {
            LinkUpdateInformation information = linkUpdate.linkUpdateInformation();
            for (LinkUpdateEvent event : information.events()) {
                StringBuilder stringMessage = new StringBuilder();
                stringMessage
                    .append("***")
                    .append(information.title())
                    .append("***\n")
                    .append("По ссылке ")
                    .append(information.uri())
                    .append(" произошло обновление!\n")
                    .append("Событие: ")
                    .append(event.type())
                    .append("\n")
                    .append("Время: ")
                    .append(event.updateTime())
                    .append("\n")
                    .append("Дополнительная информация: ");
                for (var info : event.metaInfo().entrySet()) {
                    stringMessage
                        .append(info.getKey())
                        .append(" –- ")
                        .append(info.getValue())
                        .append(",");
                }
                stringMessage.deleteCharAt(stringMessage.length() - 1);
                SendMessage message = new SendMessage(
                    chatId,
                    stringMessage.toString()
                ).parseMode(ParseMode.Markdown);
                chatResponser.sendMessage(message);
            }
        }
    }
}
