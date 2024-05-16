package ru.bytebosses.bot.models;

import ru.bytebosses.bot.api.dto.response.ApiErrorResponse;

public record GenericResponse<T>(T response, ApiErrorResponse errorResponse) {
}
