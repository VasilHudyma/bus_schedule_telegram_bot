package com.example.bus_schedule_telegram_bot.config;

import com.example.bus_schedule_telegram_bot.BusScheduleTelegramBot;
import com.example.bus_schedule_telegram_bot.botapi.TelegramFacade;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "telegram-bot")
public class BotConfig {
    private String webHookPath;
    private String botUserName;
    private String botToken;

    @Bean
    public BusScheduleTelegramBot myBusScheduleTelegramBot(TelegramFacade telegramFacade) {
        BusScheduleTelegramBot bot = new BusScheduleTelegramBot(telegramFacade);
        bot.setBotToken(botToken);
        bot.setBotUserName(botUserName);
        bot.setWebHookPath(webHookPath);
        return bot;
    }
}
