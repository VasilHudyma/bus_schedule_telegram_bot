package com.example.bus_schedule_telegram_bot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainMenuService {

    public SendMessage getMainMenuMessage(final long chatId, String textMessage) {
        final ReplyKeyboardMarkup keyboardMarkup = getMainMenuKeyboard();
        final SendMessage sendMessage =
                createMessageWithKeyboard(chatId, textMessage, keyboardMarkup);
        return sendMessage;
    }


    private ReplyKeyboardMarkup getMainMenuKeyboard() {

        final ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(false);
        keyboardMarkup.setResizeKeyboard(false);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        row1.add(new KeyboardButton(MenuState.NEXT_FROM_CITY.getTitle()));
        row1.add(new KeyboardButton(MenuState.NEXT_FROM_VILLAGE.getTitle()));
        row2.add(new KeyboardButton(MenuState.SCHEDULE_FROM_CITY.getTitle()));
        row2.add(new KeyboardButton(MenuState.SCHEDULE_FROM_VILLAGE.getTitle()));

        keyboardRows.add(row1);
        keyboardRows.add(row2);

        keyboardMarkup.setKeyboard(keyboardRows);

        return keyboardMarkup;
    }

    private SendMessage createMessageWithKeyboard(final long chatId, String textMessage,
                                                  final ReplyKeyboardMarkup keyboardMarkup) {
        return new SendMessage().setChatId(chatId).setReplyMarkup(keyboardMarkup).setText(textMessage).enableMarkdown(true);
    }
}
