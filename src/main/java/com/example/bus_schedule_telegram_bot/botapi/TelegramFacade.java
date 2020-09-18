package com.example.bus_schedule_telegram_bot.botapi;

import com.example.bus_schedule_telegram_bot.model.DayStatus;
import com.example.bus_schedule_telegram_bot.service.DirectionChoiceService;
import com.example.bus_schedule_telegram_bot.service.MainMenuService;
import com.example.bus_schedule_telegram_bot.service.MenuState;
import com.example.bus_schedule_telegram_bot.service.ScheduleService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Component
public class TelegramFacade {
    private final MainMenuService menuService;
    private final DirectionChoiceService directionChoiceService;
    private final ScheduleService scheduleService;
    private boolean isFromCity = false;
    private boolean isNearest = false;

    public TelegramFacade(MainMenuService menuService, DirectionChoiceService directionChoiceService, ScheduleService scheduleService) {
        this.menuService = menuService;
        this.directionChoiceService = directionChoiceService;
        this.scheduleService = scheduleService;
    }


    public BotApiMethod<?> handleUpdate(Update update) {

        if (update.hasCallbackQuery()) {
            return processCallbackQuery(update.getCallbackQuery());
        } else if (update.hasMessage() && update.getMessage().hasText()) {
            return handleInputMessage(update.getMessage());
        } else
            return menuService.getMainMenuMessage(update.getMessage().getChatId(), "Будь ласка оберіть один із пунктів головного меню!");

    }

    //TODO flags isNearest and isFromCity insert into map where key is chatID

    private SendMessage handleInputMessage(Message message) {

        SendMessage sendMessage;
        final long chatId = message.getChatId();
        String direction = "Оберіть напрямок:";

        if (message.getText().equals("/start")) {
            sendMessage = menuService.getMainMenuMessage(chatId, "Вітаю! Оберіть один із пунктів головного меню!");
        } else if (message.getText().equals(MenuState.SCHEDULE_FROM_CITY.getTitle())) {
            isNearest = false;
            isFromCity = true;
            sendMessage = directionChoiceService.getDirectionMessage(chatId, direction);
        } else if (message.getText().equals(MenuState.SCHEDULE_FROM_VILLAGE.getTitle())) {
            isNearest = false;
            isFromCity = false;
            sendMessage = directionChoiceService.getDirectionMessage(chatId, direction);
        } else if (message.getText().equals(MenuState.NEXT_FROM_CITY.getTitle())) {
            isNearest = true;
            isFromCity = true;
            sendMessage = directionChoiceService.getDirectionMessage(chatId, direction);
        } else if (message.getText().equals(MenuState.NEXT_FROM_VILLAGE.getTitle())) {
            isNearest = true;
            isFromCity = false;
            sendMessage = directionChoiceService.getDirectionMessage(chatId, direction);
        } else sendMessage = menuService.getMainMenuMessage(chatId, "Будь ласка оберіть один із пунктів головного меню!");

        return sendMessage;
    }

    private BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery) {

       final long chatId = callbackQuery.getMessage().getChatId();

        EditMessageText editMessageText = new EditMessageText();

        DayStatus todayDayStatus = (LocalDate.now().getDayOfWeek().equals(DayOfWeek.SATURDAY) || LocalDate.now().getDayOfWeek().equals(DayOfWeek.SUNDAY)) ?
                DayStatus.WEEKEND : DayStatus.WORKDAY;

        if (callbackQuery.getData().equals("buttonHannusivka")) {
            if (isNearest) {
                return editMessageText.setMessageId(callbackQuery.getMessage().getMessageId()).setChatId(chatId)
                        .setText(scheduleService.formNearestBusDeparture(isFromCity, true, todayDayStatus).toString());
            } else
                return editMessageText.setMessageId(callbackQuery.getMessage().getMessageId()).setChatId(chatId)
                        .setText(scheduleService.formSchedule(isFromCity, true, todayDayStatus).toString());
        } else {
            if (isNearest) {
                return editMessageText.setMessageId(callbackQuery.getMessage().getMessageId()).setChatId(chatId)
                        .setText(scheduleService.formNearestBusDeparture(isFromCity, false, todayDayStatus).toString());
            } else
                return editMessageText.setMessageId(callbackQuery.getMessage().getMessageId()).setChatId(chatId)
                        .setText(scheduleService.formSchedule(isFromCity, false, todayDayStatus).toString());
        }

    }
}
