package com.example.bus_schedule_telegram_bot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "userData")
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String languageCode;

}
