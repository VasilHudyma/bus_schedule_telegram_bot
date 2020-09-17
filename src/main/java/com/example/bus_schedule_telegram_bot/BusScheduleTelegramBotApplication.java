package com.example.bus_schedule_telegram_bot;

import com.example.bus_schedule_telegram_bot.service.data.ScheduleRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BusScheduleTelegramBotApplication implements ApplicationRunner {

    @Autowired
    private ScheduleRecordService scheduleRecordService;

    public static void main(String[] args) {
        SpringApplication.run(BusScheduleTelegramBotApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) {
//        scheduleRecordService.save(ScheduleRecord.builder().fromCity(true).throughHannusivka(true).isWeekend(true).time(LocalTime.of(13,45)).build());
//        scheduleRecordService.findAll().forEach(System.out::println);
    }
}
