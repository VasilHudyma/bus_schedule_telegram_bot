package com.example.bus_schedule_telegram_bot.service;

public enum MenuState {
    NEXT_FROM_CITY("Найближчі рейси з Франківська"),
    NEXT_FROM_VILLAGE("Найближчі рейси з Побережжя"),
    SCHEDULE_FROM_CITY("Весь розклад з Франківська"),
    SCHEDULE_FROM_VILLAGE("Весь розклад з Побережжя");

    private String title;

    MenuState(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "MenuState{" +
                "title='" + title + '\'' +
                '}';
    }
}
