package com.smith.ingestion.service;

import com.smith.ingestion.config.IngestionMapper;
import com.smith.ingestion.dto.EventDTO;
import com.smith.ingestion.request.EventRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventIngestionService {

    private static final Logger logger = LoggerFactory.getLogger(EventIngestionService.class);

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public void ingest(EventRequest request){

        EventDTO dto = IngestionMapper.toDTO(request);

        kafkaProducerService.send(dto);

    }
}
