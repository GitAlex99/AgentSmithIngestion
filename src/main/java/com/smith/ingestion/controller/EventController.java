package com.smith.ingestion.controller;

import com.smith.ingestion.request.EventRequest;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;

@RestController
@RequestMapping("api/v1")
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    @PostMapping("/events")
    public void eventIngestion(@RequestBody EventRequest request) {

        logger.info("Received event: {}", request);


    }
}
