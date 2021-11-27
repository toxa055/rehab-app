package com.rehab.controller;

import com.rehab.dto.EventMessage;
import com.rehab.service.EventService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for Event.
 * It's only used for getting event messages from another application.
 * Any method returns execution result in JSON format in response body.
 */
@RestController
public class RestEventController {

    /**
     * EventService bean.
     */
    private final EventService eventService;

    /**
     * Interface that logs specific messages.
     */
    private final Logger logger;

    /**
     * @param eventService description of eventService is in field declaration.
     * @param logger       description of logger is in field declaration.
     */
    @Autowired
    public RestEventController(EventService eventService, Logger logger) {
        this.eventService = eventService;
        this.logger = logger;
    }

    /**
     * Method executes GET request to receive today planned eventMessages.
     *
     * @return list of today planned event messages.
     */
    @RequestMapping(value = "/rest/events", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EventMessage> getTodayPlannedEvents() {
        logger.info("Get today planned events through rest.");
        return eventService.getTodayPlannedEventsMessage();
    }
}
