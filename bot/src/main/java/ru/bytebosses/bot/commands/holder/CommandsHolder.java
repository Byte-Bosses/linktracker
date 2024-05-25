package ru.bytebosses.bot.commands.holder;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import ru.bytebosses.bot.commands.CommandHandler;

/**
 * CommandsHolder class is responsible for holding command handlers and their corresponding names and descriptions.
 * It is configured as a Spring Service and scans for components in the specified base package.
 */
@Getter
@Service
@ComponentScan(basePackages = "edu.java.bot.commands")
public class CommandsHolder {

    private final Map<String, CommandHandler> commandHandlers;
    private final Map<String, String> commandsNameAndDescriptions;

    /**
     * Constructs a CommandsHolder with a list of CommandHandlers and properties.
     *
     * @param commandHandlers the list of CommandHandler instances to be managed.
     * @param properties the properties containing configuration values.
     */
    @Autowired
    public CommandsHolder(List<CommandHandler> commandHandlers, Properties properties) {
        this.commandHandlers = getMapOfCommandHandlers(commandHandlers);
        this.commandsNameAndDescriptions = new LinkedHashMap<>();
        for (CommandHandler commandHandler : commandHandlers) {
            String commandName = commandHandler.commandName();
            if (!commandName.equals(properties.getProperty("command.start.name"))) {
                String commandDescription = commandHandler.commandDescription();
                commandsNameAndDescriptions.put(commandName, commandDescription);
            }
        }
    }

    /**
     * Converts a list of CommandHandlers into a map where the keys are command names and the values are CommandHandlers.
     *
     * @param commandHandlers the list of CommandHandler instances.
     * @return a map associating command names with their corresponding CommandHandler.
     */
    private Map<String, CommandHandler> getMapOfCommandHandlers(List<CommandHandler> commandHandlers) {
        Map<String, CommandHandler> commandHandlerMap = new HashMap<>();
        for (CommandHandler handler : commandHandlers) {
            commandHandlerMap.put(handler.commandName(), handler);
        }
        return commandHandlerMap;
    }
}
