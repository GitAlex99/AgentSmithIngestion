package com.smith.ingestion.config;

import com.smith.ingestion.dto.EventDTO;
import com.smith.ingestion.request.EventRequest;

import java.time.Instant;
import java.util.UUID;

public class IngestionMapper {

    public static EventDTO toDTO(EventRequest request){
        EventDTO dto = new EventDTO();

        dto.setType(request.getType());
        dto.setSource(request.getSource());
        dto.setSeverity(request.getSeverity());
        dto.setPayload(request.getPayload());
        dto.setTimestamp(request.getTimestamp());
        dto.setClientId(request.getClientId());

        dto.setReceivedAt(Instant.now());
        dto.setVersion("1.0.0");
        dto.setId(UUID.randomUUID());

        return dto;
    }
}
