package me.alov.ytdlbot.service;

import lombok.extern.slf4j.Slf4j;
import me.alov.ytdlbot.config.BotConfig;
import me.alov.ytdlbot.handler.CallbackQueryProcessor;
import me.alov.ytdlbot.handler.MessageProcessor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

import java.util.Optional;

@Slf4j
@Component
public class SimpleWebhookBot extends SpringWebhookBot {

    private final BotConfig botConfig;
    private final MessageProcessor messageProcessor;
    private final CallbackQueryProcessor callbackQueryProcessor;

    public SimpleWebhookBot(BotConfig botConfig, SetWebhook setWebhook, MessageProcessor messageProcessor, CallbackQueryProcessor callbackQueryProcessor) {
        super(setWebhook);
        this.botConfig = botConfig;
        this.messageProcessor = messageProcessor;
        this.callbackQueryProcessor = callbackQueryProcessor;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotApiKey();
    }

    @Override
    public String getBotPath() {
        return botConfig.getWebhookPath();
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        Optional<CallbackQuery> callbackQuery = Optional.ofNullable(update.getCallbackQuery());

        callbackQuery.ifPresent(callbackQueryProcessor::processCallbackQuery);
        return callbackQuery.isPresent()
                ? callbackQueryProcessor.processCallbackQuery(callbackQuery.orElseThrow(() -> new RuntimeException("")))
                : messageProcessor.processMessage(update.getMessage());
    }


}
