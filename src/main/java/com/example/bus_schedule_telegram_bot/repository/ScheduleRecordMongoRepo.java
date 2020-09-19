package com.example.bus_schedule_telegram_bot.repository;

import com.example.bus_schedule_telegram_bot.model.ScheduleRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRecordMongoRepo extends MongoRepository<ScheduleRecord, String> {
    List<ScheduleRecord> getByFromCityAndThroughHannusivka(boolean fromCity, boolean troughHannusivka);
}
