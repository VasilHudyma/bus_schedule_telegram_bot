package com.example.bus_schedule_telegram_bot;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BusScheduleTelegramBotApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(BusScheduleTelegramBotApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) {
    }
}
