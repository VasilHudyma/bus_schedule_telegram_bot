package com.example.bus_schedule_telegram_bot.botapi;

import com.example.bus_schedule_telegram_bot.service.MainMenuService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class TelegramFacade {
    private MainMenuService menuService;

    public TelegramFacade(MainMenuService menuService) {
        this.menuService = menuService;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        return menuService.getMainMenuMessage(update.getMessage().getChatId(),
                "Replied! "+LocalTime.now(ZoneId.of("Europe/Kiev")).format(DateTimeFormatter.ofPattern("HH:mm")));

    }
}
