package com.smith.ingestion.service;

import com.smith.ingestion.dto.EventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private static final String TOPIC = "smith.events.ingestion.v1";

    @Autowired
    private KafkaTemplate<String, EventDTO> kafkaTemplate;

    public void send(EventDTO dto){
        logger.info("Sending event on Kafka with topic: {} and dto: {}", TOPIC, dto);

        kafkaTemplate.send(TOPIC, dto.getId().toString(), dto);

        logger.info("Sending of DTO: {} completed", dto.getId().toString());
    }
}
