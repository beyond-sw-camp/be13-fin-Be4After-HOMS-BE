package com.beyond.homs.common.message.discord;

public record DiscordWebhookMessage(String content) {
    public DiscordWebhookMessage {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be null or blank");
        }
    }
}
