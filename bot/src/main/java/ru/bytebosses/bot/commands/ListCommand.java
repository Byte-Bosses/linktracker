package ru.bytebosses.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bytebosses.bot.api.dto.response.GenericResponse;
import ru.bytebosses.bot.api.dto.response.ListLinksResponse;
import ru.bytebosses.bot.client.service.BotService;
import ru.bytebosses.bot.models.LinkResponse;

@Component
public class ListCommand extends CommandHandler {

    private final BotService botService;

    @Autowired
    public ListCommand(Properties properties, BotService botService) {
        super(properties);
        this.botService = botService;
    }

    @Override
    public SendMessage handleCommand(Update update) {
        long chatId = update.message().chat().id();
        GenericResponse<ListLinksResponse> response = botService.listLinksFromDatabase(chatId);
        if (response.errorResponse() == null) {
            if (response.response().linkResponses().isEmpty()) {
                return new SendMessage(
                    chatId,
                    properties.getProperty("command.list.empty")
                );
            }
            InlineKeyboardMarkup keyboardMarkup = makeKeyboard(response.response());
            return new SendMessage(
                chatId,
                properties.getProperty("command.list.listLinks.success")
            ).replyMarkup(keyboardMarkup).parseMode(ParseMode.Markdown);
        }
        String responseErrorDescription = response.errorResponse().description();
        return new SendMessage(
            chatId,
            properties.getProperty("command.list.listLinks.fail")
                .formatted(responseErrorDescription != null ? responseErrorDescription.toLowerCase() 
                           : properties.getProperty("command.internal.error"))
        );
    }



    private InlineKeyboardMarkup makeKeyboard(ListLinksResponse response) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        for (LinkResponse link : response.linkResponses()) {
            keyboardMarkup.addRow(new InlineKeyboardButton(link.url().toString()).url(link.url().toString()));
        }
        return keyboardMarkup;
    }

    @Override
    public String commandName() {
        return properties.getProperty("command.list.name");
    }

    @Override
    public String commandDescription() {
        return properties.getProperty("command.list.description");
    }
}
