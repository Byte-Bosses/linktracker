package ru.bytebosses.bot.listener.updates;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.List;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bytebosses.bot.commands.CommandHandler;
import ru.bytebosses.bot.commands.holder.CommandsHolder;
import ru.bytebosses.bot.printertochat.ChatResponser;

/**
 * MessageUpdatesListener listens for incoming updates from users and processes them.
 * It routes the updates to the appropriate command handler and sends responses.
 */
@Component
public class MessageUpdatesListener implements UpdatesListener {
    private final ChatResponser responser;
    private final CommandsHolder commandsHolder;
    private final String delimiter;
    private final String commandUnknown;
    private Counter messagesCounter;

    /**
     * Constructs a MessageUpdatesListener with the specified dependencies.
     *
     * @param responser the ChatResponser for sending messages.
     * @param commandsHolder the CommandsHolder containing command handlers.
     * @param properties the properties containing configuration values.
     * @param registry the MeterRegistry for monitoring metrics.
     */
    @Autowired
    public MessageUpdatesListener(
        ChatResponser responser,
        CommandsHolder commandsHolder,
        Properties properties,
        MeterRegistry registry
    ) {
        this.responser = responser;
        this.commandsHolder = commandsHolder;
        this.delimiter = properties.getProperty("command.untrack.callBackDelimiter");
        this.commandUnknown = properties.getProperty("command.unknown");
        this.messagesCounter = Counter
            .builder("handled_user_messages")
            .description("Count of handled user messages")
            .register(registry);
    }

    /**
     * Processes a list of updates from users.
     *
     * @param updates the list of updates to process.
     * @return an integer indicating the processing status of updates.
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            SendMessage message = handleUpdate(update);
            if (message != null) {
                messagesCounter.increment();
                responser.sendMessage(message);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Handles a single update by determining the command and routing it to the appropriate handler.
     *
     * @param update the update object to handle.
     * @return a SendMessage object containing the response message.
     */
    private SendMessage handleUpdate(Update update) {
        String command = getCommandFromUpdate(update);
        CommandHandler handler = commandsHolder.getCommandHandlers().get(command);
        SendMessage message = null;
        if (handler == null) {
            if (update.message() != null) {
                message = new SendMessage(
                    update.message().chat().id(),
                    commandUnknown
                );
            } else if (update.callbackQuery() != null) {
                message = new SendMessage(
                    update.callbackQuery().from().id(),
                    commandUnknown
                );
            } else {
                message = new SendMessage(
                    update.editedMessage().chat().id(),
                    commandUnknown
                );
            }
            return message;
        }
        if (handler.isSupportsUpdate(update)) {
            message = handler.handleCommand(update);
        }
        return message;
    }

    /**
     * Extracts the command from the update object.
     *
     * @param update the update object containing the command message.
     * @return the extracted command as a String.
     */
    private String getCommandFromUpdate(Update update) {
        if (update.message() != null && update.message().text() != null) {
            return update.message().text().trim().replaceAll("\\s+", " ").split(" ")[0];
        }
        if (update.callbackQuery() != null && update.callbackQuery().data() != null) {
            return update.callbackQuery().data().split(delimiter)[0];
        }
        return "";
    }
}
