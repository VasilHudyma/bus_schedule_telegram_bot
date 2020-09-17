package com.example.bus_schedule_telegram_bot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Data
@Builder
@Document(collection = "busScheduleRecord")
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRecord {

    @Id
    private String id;
    private boolean fromCity;
    private boolean throughHannusivka;
    private boolean isWeekend;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

}
