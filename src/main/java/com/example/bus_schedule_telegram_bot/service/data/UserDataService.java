package com.example.bus_schedule_telegram_bot.service.data;

import com.example.bus_schedule_telegram_bot.model.UserData;
import com.example.bus_schedule_telegram_bot.repository.UserDataMongoRepo;
import org.springframework.stereotype.Service;

@Service
public class UserDataService {
    private UserDataMongoRepo userDataMongoRepo;

    public UserDataService(UserDataMongoRepo userDataMongoRepo) {
        this.userDataMongoRepo = userDataMongoRepo;
    }

    public UserData save(UserData userData) {
        return userDataMongoRepo.save(userData);
    }

    public boolean existById(String id) {
        return userDataMongoRepo.existsById(id);
    }
}
