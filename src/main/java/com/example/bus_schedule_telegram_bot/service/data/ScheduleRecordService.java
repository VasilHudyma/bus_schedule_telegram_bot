package com.example.bus_schedule_telegram_bot.service.data;

import com.example.bus_schedule_telegram_bot.model.DayStatus;
import com.example.bus_schedule_telegram_bot.model.ScheduleRecord;
import com.example.bus_schedule_telegram_bot.repository.ScheduleRecordMongoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleRecordService {

    private final ScheduleRecordMongoRepo scheduleRecordMongoRepo;

    @Autowired
    public ScheduleRecordService(ScheduleRecordMongoRepo scheduleRecordMongoRepo) {
        this.scheduleRecordMongoRepo = scheduleRecordMongoRepo;
    }

    public Iterable<ScheduleRecord> findAll() {
        return scheduleRecordMongoRepo.findAll();
    }

    public ScheduleRecord save(ScheduleRecord scheduleRecord) {
        return scheduleRecordMongoRepo.save(scheduleRecord);
    }

    public List<ScheduleRecord> findByFromCityAndThroughHannusivka(boolean fromCity, boolean throughHannusivka) {
        return scheduleRecordMongoRepo.getByFromCityAndThroughHannusivka(fromCity, throughHannusivka);
    }

}
