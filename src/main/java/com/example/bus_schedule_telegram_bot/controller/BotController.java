package com.example.bus_schedule_telegram_bot.controller;

import com.example.bus_schedule_telegram_bot.BusScheduleTelegramBot;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class BotController {
    private final BusScheduleTelegramBot telegramBot;

    public BotController(BusScheduleTelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update){
        return telegramBot.onWebhookUpdateReceived(update);
    }

    @GetMapping("/status")
    public String check(){
        return "working...";
    }
}
