package com.rehab.controller;

import com.rehab.dto.EventMessage;
import com.rehab.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestEventController {

    private final EventService eventService;

    @Autowired
    public RestEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(value = "/rest/events", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EventMessage> getTodayPlannedEvents() {
        return eventService.getTodayPlannedEventsMessage();
    }
}
