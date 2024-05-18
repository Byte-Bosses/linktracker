package ru.bytebosses.bot.api.exceptions;


/**
 * IncorrectRequestParameterException is thrown when controller receives invalid data or parameters
 * **/
public class IncorrectRequestParametersException extends RuntimeException {
    public IncorrectRequestParametersException() {
        super("Некорректные параметры запроса");
    }
}
