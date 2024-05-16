package ru.bytebosses.bot.api.endpoints.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bytebosses.bot.api.dto.request.LinkUpdate;
import ru.bytebosses.bot.api.endpoints.controllers.rateLimit.RateLimit;
import ru.bytebosses.bot.api.service.LinkUpdatesService;

@Log4j2
@RestController
@RequestMapping(path = "/updates")
@RequiredArgsConstructor
public class UpdateLinkController {
    private final LinkUpdatesService service;

    @PostMapping
    @Operation(summary = "Отправить обновление")
    @RateLimit
    public void updateLinks(
        @RequestBody @Valid LinkUpdate linkUpdate,
        HttpServletRequest request
    ) {
        service.notifyUsers(linkUpdate);
    }
}
