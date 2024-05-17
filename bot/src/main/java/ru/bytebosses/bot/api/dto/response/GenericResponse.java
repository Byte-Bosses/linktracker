package ru.bytebosses.bot.api.dto.response;

public record GenericResponse<T>(T response, ApiErrorResponse errorResponse) {
}
