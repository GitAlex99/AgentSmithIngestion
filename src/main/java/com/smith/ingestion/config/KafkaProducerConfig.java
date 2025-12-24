package com.smith.ingestion.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smith.ingestion.dto.EventDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, EventDTO> producerFactoryEvent() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);

        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaProducerFactory<>(props);
    }


    @Bean
    public KafkaTemplate<String, EventDTO> kafkaTemplateEvent() {
        return new KafkaTemplate<>(producerFactoryEvent());
    }
}
