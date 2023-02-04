package com.projtest.SpringDemoBot.service;

import com.projtest.SpringDemoBot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot  extends TelegramLongPollingBot {

    final BotConfig config;
    public TelegramBot(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String massegeText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (massegeText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;

                default: sendMessege(chatId, "Sorry, command is not recognized");

            }
        }

    }

    private void startCommandReceived(long chatId, String name) {

        String answer = "Hi, " + name + ", nice to meet you!";

        sendMessege(chatId, answer);

    }

    private void sendMessege(long chatId, String textToSend) {
        SendMessage massege = new SendMessage();
        massege.setChatId(String.valueOf(chatId));
        massege.setText(textToSend);

        try {
            execute(massege);
        }
        catch (TelegramApiException e) {

        }

    }
}
