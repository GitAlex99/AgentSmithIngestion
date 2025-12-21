    package com.smith.ingestion.request;

    import com.fasterxml.jackson.databind.JsonNode;
    import com.smith.ingestion.model.EventType;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.NotNull;
    import lombok.Data;


    import java.io.Serializable;
    import java.time.Instant;
    import java.util.UUID;

    @Data
    public class EventRequest implements Serializable {
        @NotNull(message = "Event type cannot be null")
        private EventType type;
        @NotBlank(message = "source cannot be blank")
        private String source;
        @NotBlank(message = "severity cannot be blank")
        private String severity;
        private JsonNode payload;
        @NotNull(message = "timestamp cannot be null")
        private Instant timestamp;
        @NotNull(message = "clientId cannot be null")
        private UUID clientId;

    }
