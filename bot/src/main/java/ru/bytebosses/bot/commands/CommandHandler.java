package ru.bytebosses.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Properties;

/**
 * Abstract base class for command handlers.
 * Provides common functionality for handling commands and defines abstract methods
 * for specific command handling logic.
 */
public abstract class CommandHandler {

    protected final Properties properties;

    /**
     * Constructs a CommandHandler with the specified properties.
     *
     * @param properties the properties containing configuration values.
     */
    public CommandHandler(Properties properties) {
        this.properties = properties;
    }

    /**
     * Checks if the update contains a message with text.
     *
     * @param update the update object to be checked.
     * @return true if the update contains a message with text, false otherwise.
     */
    public boolean isSupportsUpdate(Update update) {
        return update.message() != null && update.message().text() != null;
    }

    /**
     * Handles the command and generates a response message.
     *
     * @param update the update object containing the command message.
     * @return a SendMessage object containing the response message.
     */
    public abstract SendMessage handleCommand(Update update);

    /**
     * Returns the name of the command.
     *
     * @return the name of the command.
     */
    public abstract String commandName();

    /**
     * Returns the description of the command.
     *
     * @return the description of the command.
     */
    public abstract String commandDescription();
}
