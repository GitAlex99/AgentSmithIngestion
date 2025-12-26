package com.smith.ingestion.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ListenTest {
/*
    private static final Logger logger = LoggerFactory.getLogger(ListenTest.class);


    @KafkaListener(topics = "smith.events.ingestion.v1", groupId = "test")
    public void test(String message){
        logger.info("Message received: {}", message );
    } */
}
