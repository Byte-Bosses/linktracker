package ru.bytebosses.bot.printertochat;

import com.pengrad.telegrambot.request.SendMessage;

/**
 * ChatResponser is an interface for sending messages to users in a chat.
 */
public interface ChatResponser {

    /**
     * Sends a message to a chat.
     *
     * @param message the SendMessage object containing the message to be sent.
     */
    void sendMessage(SendMessage message);
}
