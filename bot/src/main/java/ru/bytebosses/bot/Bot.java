package ru.bytebosses.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SetMyCommands;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bytebosses.bot.commands.holder.CommandsHolder;

/**
 * Bot is responsible for initializing the Telegram bot, setting up the command list,
 * and registering the updates listener.
 */
@Component
public class Bot {

    private final CommandsHolder commandsHolder;
    private final TelegramBot bot;
    private final UpdatesListener listener;

    /**
     * Constructs a Bot with the specified dependencies.
     *
     * @param commandsHolder the CommandsHolder containing command handlers.
     * @param bot the TelegramBot instance for interacting with the Telegram API.
     * @param listener the UpdatesListener for processing incoming updates.
     */
    @Autowired
    public Bot(CommandsHolder commandsHolder, TelegramBot bot, UpdatesListener listener) {
        this.commandsHolder = commandsHolder;
        this.bot = bot;
        this.listener = listener;
    }

    /**
     * Initializes the bot by setting up the command list and updates listener.
     */
    @PostConstruct
    public void startUpBot() {
        setUpCommandsList();
        setUpUpdatesListener();
    }

    /**
     * Sets up the bot's command list using the commands from the CommandsHolder.
     */
    private void setUpCommandsList() {
        Map<String, String> commandsNameAndDescriptions = commandsHolder.getCommandsNameAndDescriptions();
        BotCommand[] menuCommands = new BotCommand[commandsNameAndDescriptions.size()];
        int index = 0;
        for (Map.Entry<String, String> entry : commandsNameAndDescriptions.entrySet()) {
            menuCommands[index] = new BotCommand(entry.getKey(), entry.getValue());
            index++;
        }
        SetMyCommands setMyCommands = new SetMyCommands(menuCommands);
        bot.execute(setMyCommands);
    }

    /**
     * Registers the updates listener with the bot to handle incoming updates.
     */
    private void setUpUpdatesListener() {
        bot.setUpdatesListener(listener);
    }
}
