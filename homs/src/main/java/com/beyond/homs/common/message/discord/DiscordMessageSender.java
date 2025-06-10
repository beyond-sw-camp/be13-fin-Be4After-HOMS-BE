package com.beyond.homs.common.message.discord;

import com.beyond.homs.common.message.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Slf4j
@Service
public class DiscordMessageSender implements MessageSender {
    private final WebClient webClient;

    public DiscordMessageSender(@Value("${discord.webhook.url}") String webhookUrl) {
        this.webClient = WebClient.create(webhookUrl);
    }

    @Override
    public void send(String content) {
        webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new DiscordWebhookMessage(content))
                .retrieve()
                .toBodilessEntity()
                .onErrorMap(e -> {  // 에러 변환
                    log.error("에러 발생", e);
                    return new RuntimeException(e.getMessage());
                })
                .subscribe();
    }
}
