package com.smith.ingestion.service;

import com.smith.ingestion.config.PriorityConfig;
import com.smith.ingestion.dto.EventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

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
        int partition = 0;
        dto.setSeverity(priority);

        String topic = switch (dto.getType()) {
            case USER_LOGIN, USER_LOGOUT, USER_FAILED_LOGIN, USER_MULTIPLE_FAILED_LOGIN -> USERLOGTOPIC;
            case PAYMENT_SUCCESS, PAYMENT_FAILED -> PAYMENTTOPIC;
            case ORDER_PLACED, ORDER_CANCELLED -> ORDERTOPIC;
            default -> DEFAULTTOPIC;
        };
        if(StringUtils.equalsIgnoreCase(dto.getType().toString(),"USER_LOGIN")
                || StringUtils.equalsIgnoreCase(dto.getType().toString(),"USER_LOGOUT")
                || StringUtils.equalsIgnoreCase(dto.getType().toString(),"USER_FAILED_LOGIN")) {
            partition = switch (priority) {
                case "HIGH" -> priorityConfig.calculatePartitionHighLog();
                case "MEDIUM" -> priorityConfig.calculatePartitionMediumLog();
                default -> 5;
            };
        }
        if(StringUtils.equalsIgnoreCase(dto.getType().toString(),"PAYMENT_SUCCESS")
                || StringUtils.equalsIgnoreCase(dto.getType().toString(),"PAYMENT_FAILED")) {
            partition = switch (priority) {
                case "HIGH" -> priorityConfig.calculatePartitionHighPayment();
                case "MEDIUM" -> 2;
                default -> 3;
            };
        }
        if(StringUtils.equalsIgnoreCase(dto.getType().toString(),"ORDER_PLACED")
                || StringUtils.equalsIgnoreCase(dto.getType().toString(),"ORDER_CANCELLED")) {
            partition = switch (priority) {
                case "HIGH" -> priorityConfig.calculatePartitionHighOrder();
                case "MEDIUM" -> 2;
                default -> 3;
            };
        }

        logger.info("Sending event on Kafka with topic: {} and dto: {} with priority: {}", topic, dto,priority);

        kafkaTemplate.send(topic,partition, dto.getId().toString(), dto);

        logger.info("Sending of DTO: {} completed", dto.getId().toString());
    }
}
