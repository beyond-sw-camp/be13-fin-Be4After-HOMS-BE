package com.beyond.homs.common.message.discord;

import com.beyond.homs.common.message.MessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
@Service
public class DiscordMessageSender implements MessageSender {
    @Value("${discord.webhook.url}")
    private String webhookUrl;

    @Override
    public void send(String content) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", "application/json; utf-8");
            HttpEntity<DiscordWebhookMessage> messageEntity = new HttpEntity<>(new DiscordWebhookMessage(content), httpHeaders);
            RestTemplate template = new RestTemplate();
            ResponseEntity<String> response = template.exchange(
                    webhookUrl,
                    POST,
                    messageEntity,
                    String.class
            );

            if (response.getStatusCode().value() != NO_CONTENT.value()) {
                log.error("메시지 전송 이후 에러 발생");
            }
        } catch (RuntimeException e) {
            log.error("Discord 메시지 전송 실패: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
