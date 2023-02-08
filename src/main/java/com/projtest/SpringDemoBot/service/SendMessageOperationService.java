package com.projtest.SpringDemoBot.service;

import com.projtest.SpringDemoBot.constant.VarConstant;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

import static java.lang.Math.toIntExact;
import static java.util.Arrays.asList;
@Component
public class SendMessageOperationService {
    private final String GREETING_MESSAGE = "Привет, приступим к планированию";
    private final String PLANNING_MESSAGE = "Вводите дела, после планирования нажмите конпку \"Закончить планирование\"";
    private final String END_PLANNING_MESSAGE = "Планирование окончено, для просмотра нажмите кнопку \"Показать дела\"";
    private final String INSTRUCTION = "Хотите ознакомиться с инструкцией?";
    private final ButtonsService buttonsService = new ButtonsService();

    public SendMessage createGreetingInformation(Update update) {
        SendMessage message = createSimpleMessage(update, GREETING_MESSAGE);
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(
                        asList(VarConstant.START_PLANNING, VarConstant.END_PLANNING, VarConstant.SHOW_DEALS)));
        message.setReplyMarkup(keyboardMarkup);
        return message;
    }

    public SendMessage createPlanningMessage(Update update) {
        return createSimpleMessage(update, PLANNING_MESSAGE);

    }

    public SendMessage createEndPlanningMessage(Update update) {
        return createSimpleMessage(update, END_PLANNING_MESSAGE);

    }

    public SendMessage createSimpleMessage(Update update, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText(message);
        return sendMessage;
    }


    public SendMessage createSimpleMessage(Update update, List<String> messages) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        StringBuilder message = new StringBuilder();
        for (String s : messages) {
            message.append(s).append("\n");
        }
        sendMessage.setText(message.toString());
        return sendMessage;
    }

    public SendMessage createInstructionMessage(Update update) {
        SendMessage sendMessage = createSimpleMessage(update, INSTRUCTION);
        InlineKeyboardMarkup keyboardMarkup =
                buttonsService.setInlineKeyMarkup(buttonsService.createInlineButton(VarConstant.YES));
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public EditMessageText createEditMessage(Update update, String instruction) {
        EditMessageText editMessageText = new EditMessageText();
        long mesId = update.getCallbackQuery().getMessage().getMessageId();
        long chatIdId = update.getCallbackQuery().getMessage().getChatId();
        editMessageText.setChatId(String.valueOf(chatIdId));
        editMessageText.setMessageId(toIntExact(mesId));
        editMessageText.setText(instruction);
        return editMessageText;
    }
}