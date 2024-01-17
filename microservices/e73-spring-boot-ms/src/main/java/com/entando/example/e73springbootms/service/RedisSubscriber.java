/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entando.example.e73springbootms.service;

import com.entando.example.e73springbootms.redis.RedisNotifyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisSubscriber {
    
    private static final Logger logger = LoggerFactory.getLogger(RedisSubscriber.class);
    
    @Autowired
    public RedisSubscriber(RedisNotifyManager notifyManager) {
        notifyManager.addListener("cms-content", new EventListener());
        logger.info("Added Event Listener");
    }
    
}
