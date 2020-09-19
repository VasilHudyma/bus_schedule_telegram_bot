package com.example.bus_schedule_telegram_bot.botapi;

import com.example.bus_schedule_telegram_bot.model.DayStatus;
import com.example.bus_schedule_telegram_bot.model.UserData;
import com.example.bus_schedule_telegram_bot.service.DirectionChoiceService;
import com.example.bus_schedule_telegram_bot.service.MainMenuService;
import com.example.bus_schedule_telegram_bot.service.MenuState;
import com.example.bus_schedule_telegram_bot.service.ScheduleService;
import com.example.bus_schedule_telegram_bot.service.data.UserDataService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class TelegramFacade {
    private final MainMenuService menuService;
    private final DirectionChoiceService directionChoiceService;
    private final ScheduleService scheduleService;
    private final UserDataService userDataService;

    private Map<Long, MenuState> messageMenuStateMap;
    private final Map<Long, Map<Long, MenuState>> chatIdMessageMenuStateMap = new HashMap<>();

    public TelegramFacade(MainMenuService menuService, DirectionChoiceService directionChoiceService, ScheduleService scheduleService, UserDataService userDataService) {
        this.menuService = menuService;
        this.directionChoiceService = directionChoiceService;
        this.scheduleService = scheduleService;
        this.userDataService = userDataService;
    }

    public BotApiMethod<?> handleUpdate(Update update) {

        if (update.hasCallbackQuery()) {
            if (!chatIdMessageMenuStateMap.containsKey(update.getCallbackQuery().getMessage().getChatId())) {
                chatIdMessageMenuStateMap.put(update.getCallbackQuery().getMessage().getChatId(), new HashMap<>());
            }

            return processCallbackQuery(update.getCallbackQuery());
        } else if (update.hasMessage() && update.getMessage().hasText()) {
            if (!chatIdMessageMenuStateMap.containsKey(update.getMessage().getChatId())) {
                chatIdMessageMenuStateMap.put(update.getMessage().getChatId(), new HashMap<>());
            }

            return handleInputMessage(update.getMessage());
        } else
            return menuService.getMainMenuMessage(update.getMessage().getChatId(), "Будь ласка оберіть один із пунктів головного меню!");

    }

    private SendMessage handleInputMessage(Message message) {

        final long chatId = message.getChatId();
        final long messageId = message.getMessageId();

        String directionText = "Оберіть напрямок:";
        messageMenuStateMap = chatIdMessageMenuStateMap.get(chatId);

        User user = message.getFrom();

        if (!userDataService.existById(user.getId().toString())) {
            userDataService.save(UserData.builder()
                    .userId(user.getId().toString())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .userName(user.getUserName())
                    .languageCode(user.getLanguageCode()).build());
        }

        if (message.getText().equals("/start")) {
            return menuService.getMainMenuMessage(chatId, "Вітаю! Оберіть один із пунктів головного меню!");
        } else if (message.getText().equals(MenuState.SCHEDULE_FROM_CITY.getTitle())) {

            messageMenuStateMap.put(messageId, MenuState.SCHEDULE_FROM_CITY);

            return directionChoiceService.getDirectionMessage(chatId, directionText).setReplyToMessageId(message.getMessageId());
        } else if (message.getText().equals(MenuState.SCHEDULE_FROM_VILLAGE.getTitle())) {

            messageMenuStateMap.put(messageId, MenuState.SCHEDULE_FROM_VILLAGE);

            return directionChoiceService.getDirectionMessage(chatId, directionText).setReplyToMessageId(message.getMessageId());
        } else if (message.getText().equals(MenuState.NEXT_FROM_CITY.getTitle())) {

            messageMenuStateMap.put(messageId, MenuState.NEXT_FROM_CITY);

            return directionChoiceService.getDirectionMessage(chatId, directionText).setReplyToMessageId(message.getMessageId());
        } else if (message.getText().equals(MenuState.NEXT_FROM_VILLAGE.getTitle())) {

            messageMenuStateMap.put(messageId, MenuState.NEXT_FROM_VILLAGE);

            return directionChoiceService.getDirectionMessage(chatId, directionText).setReplyToMessageId(message.getMessageId());
        } else
            return menuService.getMainMenuMessage(chatId, "Будь ласка оберіть один із пунктів головного меню!");

    }

    private BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery) {

        final long chatId = callbackQuery.getMessage().getChatId();
        final long repliedMessageId = callbackQuery.getMessage().getReplyToMessage().getMessageId();
        boolean isNext = false;
        boolean isFromCity = false;

        EditMessageText editMessageText = new EditMessageText();
        DayStatus todayDayStatus = (LocalDate.now().getDayOfWeek().equals(DayOfWeek.SATURDAY) || LocalDate.now().getDayOfWeek().equals(DayOfWeek.SUNDAY)) ?
                DayStatus.WEEKEND : DayStatus.WORKDAY;
        MenuState menuState = messageMenuStateMap.remove(repliedMessageId);

        switch (menuState) {
            case NEXT_FROM_CITY -> {
                isNext = true;
                isFromCity = true;
            }
            case NEXT_FROM_VILLAGE -> isNext = true;
            case SCHEDULE_FROM_CITY -> isFromCity = true;
        }

        if (callbackQuery.getData().equals("buttonHannusivka")) {
            if (isNext) {
                return editMessageText.setMessageId(callbackQuery.getMessage().getMessageId())
                        .setChatId(chatId)
                        .setText(scheduleService.formNearestBusDeparture(isFromCity, true, todayDayStatus).toString());
            } else
                return editMessageText.setMessageId(callbackQuery.getMessage().getMessageId())
                        .setChatId(chatId)
                        .setText(scheduleService.formSchedule(isFromCity, true, todayDayStatus).toString());
        } else {
            if (isNext) {
                return editMessageText.setMessageId(callbackQuery.getMessage().getMessageId())
                        .setChatId(chatId)
                        .setText(scheduleService.formNearestBusDeparture(isFromCity, false, todayDayStatus).toString());
            } else
                return editMessageText.setMessageId(callbackQuery.getMessage().getMessageId())
                        .setChatId(chatId)
                        .setText(scheduleService.formSchedule(isFromCity, false, todayDayStatus).toString());
        }
    }
}
