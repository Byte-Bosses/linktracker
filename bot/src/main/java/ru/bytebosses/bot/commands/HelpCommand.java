package ru.bytebosses.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Map;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.bytebosses.bot.commands.holder.CommandsHolder;

@Component
public class HelpCommand extends CommandHandler {
    private final CommandsHolder commandsHolder;

    @Autowired
    public HelpCommand(Properties properties, @Lazy CommandsHolder commandsHolder) {
        super(properties);
        this.commandsHolder = commandsHolder;
    }

    @Override
    public SendMessage handleCommand(Update update) {
        long chatId = update.message().chat().id();
        Map<String, String> commandsNameAndDescription = commandsHolder.getCommandsNameAndDescriptions();
        StringBuilder response = new StringBuilder(properties.getProperty("command.help.listCommands"));
        for (Map.Entry<String, String> entry : commandsNameAndDescription.entrySet()) {
            response
                .append("\n")
                .append(entry.getKey())
                .append(" - ")
                .append(entry.getValue().toLowerCase());
        }
        return new SendMessage(
            chatId,
            response.toString()
        ).parseMode(ParseMode.Markdown);
    }

    @Override
    public String commandName() {
        return properties.getProperty("command.help.name");
    }

    @Override
    public String commandDescription() {
        return properties.getProperty("command.help.description");
    }
}
