package com.rehab.controller;

import com.rehab.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/events")
@Secured({"ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_NURSE"})
public class EventController {

    private static final String EVENTS = "events";
    private static final String EVENTS_LIST = "/events/list";
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{eventId}")
    public String getById(@PathVariable int eventId, Model model) {
        model.addAttribute("event", eventService.getById(eventId));
        return "/events/event";
    }

    @GetMapping("/patient/{patientId}")
    public String getAllByPatientId(@PathVariable int patientId, Model model) {
        model.addAttribute(EVENTS, eventService.getAllByPatientId(patientId));
        return EVENTS_LIST;
    }

    @GetMapping("/nurse/{nurseId}")
    public String getAllByNurseId(@PathVariable int nurseId, Model model) {
        model.addAttribute(EVENTS, eventService.getAllByNurseId(nurseId));
        return EVENTS_LIST;
    }

    @GetMapping("/filter")
    public String filter(@RequestParam @Nullable
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate plannedDate,
                         @RequestParam @Nullable Integer insuranceNumber,
                         Model model) {
        if ((plannedDate != null) && (insuranceNumber != null)) {
            model.addAttribute(EVENTS, eventService.getAllByInsuranceNumberAndPlannedDate(insuranceNumber,
                    plannedDate));
        }
        if ((plannedDate == null) && (insuranceNumber != null)) {
            model.addAttribute(EVENTS, eventService.getAllByInsuranceNumber(insuranceNumber));
        }
        if ((plannedDate != null) && (insuranceNumber == null)) {
            model.addAttribute(EVENTS, eventService.getAllByPlannedDate(plannedDate));
        }
        if ((plannedDate == null) && (insuranceNumber == null)) {
            model.addAttribute(EVENTS, eventService.getAll());
        }
        return EVENTS_LIST;
    }

    @Secured("ROLE_NURSE")
    @GetMapping("/choose/{eventId}")
    public String choose(@PathVariable int eventId) {
        eventService.setNurse(eventId);
        return "redirect:..";
    }

    @Secured("ROLE_NURSE")
    @GetMapping("/discard/{eventId}")
    public String discard(@PathVariable int eventId) {
        eventService.unSetNurse(eventId);
        return "redirect:..";
    }

    @Secured("ROLE_NURSE")
    @GetMapping("/change/{eventId}")
    public String changeState(@PathVariable int eventId, @RequestParam String state) {
        eventService.changeStatus(eventId, state);
        return "redirect:..";
    }

    @GetMapping
    public String events(Model model) {
        model.addAttribute(EVENTS, eventService.getAll());
        return EVENTS_LIST;
    }

    @GetMapping("/today")
    public String todayEvents(Model model) {
        model.addAttribute(EVENTS, eventService.getAllByPlannedDate(LocalDate.now()));
        return EVENTS_LIST;
    }
}
