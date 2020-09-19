package com.example.bus_schedule_telegram_bot.repository;

import com.example.bus_schedule_telegram_bot.model.UserData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataMongoRepo extends MongoRepository<UserData, String> {
}
