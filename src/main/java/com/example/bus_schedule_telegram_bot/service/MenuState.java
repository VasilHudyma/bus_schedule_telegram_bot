package com.example.bus_schedule_telegram_bot.service;

public enum MenuState {
    NEXT_FROM_CITY("Найближчі рейси з Івано-Франківська"),
    NEXT_FROM_VILLAGE("Найближчі рейси з Побережжя"),
    SCHEDULE_FROM_CITY("Розклад автобусів, які виїзджають з Івано-Франківська"),
    SCHEDULE_FROM_VILLAGE("Розклад автобусів, які виїзджають з Побережжя");

    private final String title;

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
