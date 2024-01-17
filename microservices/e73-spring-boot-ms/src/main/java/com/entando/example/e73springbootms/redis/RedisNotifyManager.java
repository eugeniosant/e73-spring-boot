/*
 * Copyright 2024-Present Entando Inc. (http://www.entando.com) All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.entando.example.e73springbootms.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisNotifyManager {
    
    private static final Logger logger = LoggerFactory.getLogger(RedisNotifyManager.class);
    
    private StatefulRedisPubSubConnection<String, String> subConn;
    private final RedisClient redisClient;
    
    public RedisNotifyManager(@Autowired(required = false) RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    @PreDestroy
    public void destroy() {
        if (null != subConn) {
            this.subConn.close();
        }
    }

    public void addListener(String channel, RedisPubSubListener<String, String> listener) {
        if (null == this.redisClient) {
            logger.warn("Redis not active - listener not added");
            return;
        }
        StatefulRedisPubSubConnection<String, String> connection = this.getSubConnection();
        if (null != connection) {
            connection.addListener(listener);
            connection.async().subscribe(channel);
            logger.info("Registered listener {} - channel {}", listener.getClass().getName(), channel);
        } else {
            logger.error("subscribe - null StatefulRedisPubSubConnection from RedisClient {} ", this.redisClient);
        }
    }
    
    private StatefulRedisPubSubConnection<String, String> getSubConnection() {
        if (null == this.subConn) {
            subConn = this.redisClient.connectPubSub();
        }
        return this.subConn;
    }

}
