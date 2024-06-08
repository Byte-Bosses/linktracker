package ru.bytebosses.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bytebosses.bot.api.dto.response.GenericResponse;
import ru.bytebosses.bot.client.service.BotService;
import ru.bytebosses.bot.models.Chat;

@Component
public class StartCommand extends CommandHandler {

    private final BotService botService;

    @Autowired
    public StartCommand(Properties properties, BotService botService) {
        super(properties);
        this.botService = botService;
    }

    @Override
    public SendMessage handleCommand(Update update) {
        long chatId = update.message().chat().id();
        GenericResponse<Void> response = botService.registerUser(new Chat(chatId));
        if (response.errorResponse() == null) {
            return new SendMessage(
                chatId,
                properties.getProperty("command.start.hello")
            ).parseMode(ParseMode.Markdown);
        }
        String responseErrorDescription = response.errorResponse().description();
        return new SendMessage(
            chatId,
            properties.getProperty("command.start.failedRegistration")
                .formatted(responseErrorDescription != null ? responseErrorDescription.toLowerCase()
                    : properties.getProperty("command.internal.error"))
        );
    }

    @Override
    public String commandName() {
        return properties.getProperty("command.start.name");
    }

    @Override
    public String commandDescription() {
        return properties.getProperty("command.start.description");
    }
}
