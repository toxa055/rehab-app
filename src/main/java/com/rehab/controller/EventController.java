package com.rehab.controller;

import com.rehab.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/events")
public class EventController {

    private static final String EVENTS_LIST = "/events/list";
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(value = "/getBy", params = "id")
    public String getById(@RequestParam int id, Model model) {
        model.addAttribute("event", eventService.getById(id));
        return "/events/event";
    }

    @GetMapping(value = "/getBy", params = "patientId")
    public String getAllByPatientId(@RequestParam int patientId, Model model) {
        model.addAttribute("events", eventService.getAllByPatientId(patientId));
        return EVENTS_LIST;
    }

    @GetMapping(value = "/getBy", params = "nurseId")
    public String getAllByNurseId(@RequestParam int nurseId, Model model) {
        model.addAttribute("events", eventService.getAllByNurseId(nurseId));
        return EVENTS_LIST;
    }

    @GetMapping
    public String events(Model model) {
        model.addAttribute("events", eventService.getAll());
        return EVENTS_LIST;
    }

    @GetMapping("/today")
    public String todayEvents(Model model) {
        model.addAttribute("events", eventService.getAllToday());
        return EVENTS_LIST;
    }
}
