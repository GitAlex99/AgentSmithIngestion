package com.smith.ingestion.service;

import com.smith.ingestion.config.IngestionMapper;
import com.smith.ingestion.dto.EventDTO;
import com.smith.ingestion.request.EventRequest;
import org.springframework.stereotype.Service;

@Service
public class EventIngestionService {

    public void ingest(EventRequest request){

        EventDTO dto = IngestionMapper.toDTO(request);


    }
}
