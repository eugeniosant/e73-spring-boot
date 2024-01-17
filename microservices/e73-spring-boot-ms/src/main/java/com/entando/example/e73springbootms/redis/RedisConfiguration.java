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
import io.lettuce.core.RedisURI;
import io.lettuce.core.resource.DefaultClientResources;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author E.Santoboni
 */
@Configuration
public class RedisConfiguration {
    
    private static final Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);
    
    private static final String REDIS_PREFIX = "redis://";
    
    @Value("${REDIS_ACTIVE:false}")
    private boolean active;

    @Value("${REDIS_ADDRESS:redis://localhost:6379}")
    private String redisAddress;

    @Value("${REDIS_ADDRESSES:}")
    private String redisAddresses;

    @Value("${REDIS_MASTER_NAME:mymaster}")
    private String redisMasterName;

    @Value("${REDIS_PASSWORD:}")
    private String redisPassword;
    
    @Bean
    public RedisClient getRedisClient() {
        if (!this.active) {
            return null;
        }
        DefaultClientResources resources = DefaultClientResources.builder().build();
        RedisClient lettuceClient = null;
        if (!StringUtils.isBlank(this.redisAddresses)) {
            logger.warn("** Client-side caching doesn't work on Redis Cluster and sharding data environments but only for Master/Slave environments (with sentinel) **");
            List<String> purgedAddresses = new ArrayList<>();
            String[] addresses = this.redisAddresses.split(",");
            for (int i = 0; i < addresses.length; i++) {
                String address = addresses[i];
                if (!address.trim().startsWith(REDIS_PREFIX)) {
                    purgedAddresses.add(address.trim());
                } else {
                    purgedAddresses.add(address.trim().substring(REDIS_PREFIX.length()));
                }
            }
            RedisURI.Builder uriBuilder = RedisURI.builder();
            if (addresses.length > 1) {
                for (int i = 0; i < purgedAddresses.size(); i++) {
                    String[] sections = purgedAddresses.get(i).split(":");
                    uriBuilder.withSentinel(sections[0], Integer.parseInt(sections[1]));
                }
            }
            uriBuilder.withSentinelMasterId(this.redisMasterName);
            if (!StringUtils.isBlank(this.redisPassword)) {
                uriBuilder.withPassword(this.redisPassword.toCharArray());
            }
            RedisURI redisUri = uriBuilder.build();
            lettuceClient = new RedisClient(resources, redisUri) {};
        } else {
            RedisURI redisUri = RedisURI.create((this.redisAddress.startsWith(REDIS_PREFIX)) ? this.redisAddress : REDIS_PREFIX + this.redisAddress);
            if (!StringUtils.isBlank(this.redisPassword)) {
                redisUri.setPassword(this.redisPassword.toCharArray());
            }
            lettuceClient = new RedisClient(resources, redisUri) {};
        }
        return lettuceClient;
    }
    
}
