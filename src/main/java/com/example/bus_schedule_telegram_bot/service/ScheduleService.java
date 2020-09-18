package com.example.bus_schedule_telegram_bot.service;

import com.example.bus_schedule_telegram_bot.model.DayStatus;
import com.example.bus_schedule_telegram_bot.model.ScheduleRecord;
import com.example.bus_schedule_telegram_bot.service.data.ScheduleRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRecordService scheduleRecordService;

    public ScheduleService(ScheduleRecordService scheduleRecordService) {
        this.scheduleRecordService = scheduleRecordService;
    }

    public StringBuilder formSchedule(boolean isFromCity, boolean isThroughHannusivka, DayStatus dayStatus) {

        StringBuilder stringBuilder = new StringBuilder()
                .append("Розклад автобусів, які виїзджають з \n").append(isFromCity ? "Івано-Франківська" : "Побережжя")
                .append(" та їдуть через ").append(isThroughHannusivka ? "Ганнусівку:" : "Єзупіль:").append("\n\n");

        scheduleRecordService.findByFromCityAndThroughHannusivka(isFromCity, isThroughHannusivka).stream()
                .filter(scheduleRecord -> scheduleRecord.getDayStatus().equals(dayStatus) || scheduleRecord.getDayStatus().equals(DayStatus.ALL))
                .sorted(Comparator.comparing(ScheduleRecord::getTime))
                .forEach(r -> stringBuilder
                .append(r.getTime().minusHours(calculateDifferenceBetweenTimezone()))
                .append("\n"));

        return stringBuilder;
    }

    public StringBuilder formNearestBusDeparture(boolean isFromCity, boolean isThroughHannusivka, DayStatus dayStatus) {

        StringBuilder stringBuilder = new StringBuilder()
                .append("Найближчі рейси, які виїзджають з \n").append(isFromCity ? "Івано-Франківська" : "Побережжя")
                .append(" та їдуть через ").append(isThroughHannusivka ? "Ганнусівку:" : "Єзупіль:").append("\n\n");


        scheduleRecordService.findByFromCityAndThroughHannusivka(isFromCity, isThroughHannusivka).stream()
                .filter(scheduleRecord -> scheduleRecord.getDayStatus().equals(dayStatus) || scheduleRecord.getDayStatus().equals(DayStatus.ALL))
                .sorted(Comparator.comparing(ScheduleRecord::getTime))
                .filter(scheduleRecord -> scheduleRecord.getTime().minusHours(calculateDifferenceBetweenTimezone()).isAfter(LocalTime.now(ZoneId.of("Europe/Kiev"))))
                .limit(2)
                .collect(Collectors.toList())
                .forEach(scheduleRecord -> stringBuilder.append(scheduleRecord.getTime().minusHours(calculateDifferenceBetweenTimezone()))
                        .append("\n"));

        return stringBuilder;
    }

    private long calculateDifferenceBetweenTimezone() {
        return LocalTime.now(ZoneId.of("Europe/Kiev")).getHour() - LocalTime.now(ZoneId.of("UTC")).getHour();
    }
}
