package com.example.bus_schedule_telegram_bot.service;

public enum DirectionState {
    THROUGH_HANNUSIVKA("Ч-з Ганнусівку"),
    THROUGH_YEZUPIL("Ч-з Єзупіль");

    private String title;

    DirectionState(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "DirectionState{" +
                "title='" + title + '\'' +
                '}';
    }
}
