package ru.bytebosses.bot.printertochat;

import com.pengrad.telegrambot.request.SendMessage;

public interface ChatResponser {
    void sendMessage(SendMessage message);
}
