package com.rehab.controller;

import com.rehab.dto.EventMessage;
import com.rehab.service.EventService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestEventController {

    private final EventService eventService;
    private final Logger logger;

    @Autowired
    public RestEventController(EventService eventService, Logger logger) {
        this.eventService = eventService;
        this.logger = logger;
    }

    @RequestMapping(value = "/rest/events", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EventMessage> getTodayPlannedEvents() {
        logger.info("Get today planned events through rest.");
        return eventService.getTodayPlannedEventsMessage();
    }
}
