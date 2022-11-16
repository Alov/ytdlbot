package me.alov.ytdlbot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Data
@Configuration
public class BotConfig {

    @Value("${tg.bot.name}")
    String botName;
    @Value("${tg.api.key}")
    String botApiKey;
    @Value("${tg.webhook.path}")
    String webhookPath;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(webhookPath).build();
    }
}
