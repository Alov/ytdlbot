package me.alov.ytdlbot.handler;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageProcessor {
    BotApiMethod processMessage(Message message);
}
