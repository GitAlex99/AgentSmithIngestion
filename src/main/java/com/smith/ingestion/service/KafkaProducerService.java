package com.smith.ingestion.service;

import com.smith.ingestion.config.PriorityConfig;
import com.smith.ingestion.dto.EventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private final String USERLOGTOPIC = "smith.events.ingestion.v1.user.login";
    private final String PAYMENTTOPIC = "smith.events.ingestion.v1.payment";
    private final String ORDERTOPIC = "smith.events.ingestion.v1.order";
    private final String DEFAULTTOPIC = "smith.events.ingestion.v1.default";


    @Autowired
    @Qualifier(value = "kafkaTemplateEvent")
    private KafkaTemplate<String, EventDTO> kafkaTemplate;

    @Autowired
    private PriorityConfig priorityConfig;

    public void send(EventDTO dto){

        String priority = priorityConfig.getPriority(dto.getType().toString());
        dto.setSeverity(priority);

        String topic = switch (dto.getType()) {
            case USER_LOGIN, USER_LOGOUT, USER_FAILED_LOGIN, USER_MULTIPLE_FAILED_LOGIN -> USERLOGTOPIC;
            case PAYMENT_SUCCESS, PAYMENT_FAILED -> PAYMENTTOPIC;
            case ORDER_PLACED, ORDER_CANCELLED -> ORDERTOPIC;
            default -> DEFAULTTOPIC;
        };

        logger.info("Sending event on Kafka with topic: {} and dto: {} with priority: {}", topic, dto,priority);

        kafkaTemplate.send(topic, dto.getId().toString(), dto);

        logger.info("Sending of DTO: {} completed", dto.getId().toString());
    }
}
