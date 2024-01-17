package com.entando.example.e73springbootms.service;

import io.lettuce.core.pubsub.RedisPubSubListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventListener implements RedisPubSubListener<String, String> {

    private static final Logger logger =  LoggerFactory.getLogger(EventListener.class);

    protected EventListener() {
    }

    @Override
    public void message(String channel, String message) {
        logger.info(String.format("Channel: %s, Message: %s", channel, message));
        this.manageEvent(channel, message);
    }

    @Override
    public void message(String pattern, String channel, String message) {
        logger.info(String.format("pattern: %s, Channel: %s, Message: %s", pattern, channel, message));
        this.manageEvent(channel, message);
    }

    private void manageEvent(String channel, String message) {
        // manage Event
    }

    @Override
    public void subscribed(String channel, long count) {
        logger.info(String.format("subscribed - channel: %s, count: %s", channel, count));
    }

    @Override
    public void psubscribed(String pattern, long count) {
        logger.info(String.format("psubscribed - pattern: %s, pattern: %s", pattern, count));
    }

    @Override
    public void unsubscribed(String channel, long count) {
        logger.info(String.format("unsubscribed - channel: %s, count: %s", channel, count));
    }

    @Override
    public void punsubscribed(String pattern, long count) {
        logger.info(String.format("punsubscribed - pattern: %s, count: %s", pattern, count));
    }

}
