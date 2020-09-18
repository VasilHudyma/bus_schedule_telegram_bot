package com.example.bus_schedule_telegram_bot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectionChoiceService {

    public SendMessage getDirectionMessage(final long chatId, String text) {
        return createMessageWithInlineKeyboard(chatId, text, getInlineKeyboardMarkup());
    }

    private InlineKeyboardMarkup getInlineKeyboardMarkup() {

        final InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonHannusivka = new InlineKeyboardButton().setText(DirectionState.THROUGH_HANNUSIVKA.getTitle());
        InlineKeyboardButton buttonYezupil = new InlineKeyboardButton().setText(DirectionState.THROUGH_YEZUPIL.getTitle());

        buttonHannusivka.setCallbackData("buttonHannusivka");
        buttonYezupil.setCallbackData("buttonYezupil");

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(buttonHannusivka);
        row.add(buttonYezupil);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }

    private SendMessage createMessageWithInlineKeyboard(final long chatId, String text, InlineKeyboardMarkup keyboard) {
        return new SendMessage().setChatId(chatId).setReplyMarkup(keyboard).setText(text);
    }

}
