package ru.bytebosses.bot.api.exceptions;

/**
 * IncorrectRequestParameterException is thrown when limit on requests to endpoint is exceeded
 * **/

public class TooManyRequestsException extends RuntimeException {
    public TooManyRequestsException() {
        super("Превышен лимит запросов");
    }
}
