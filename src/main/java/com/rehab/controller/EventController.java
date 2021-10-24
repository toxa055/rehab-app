package com.rehab.controller;

import com.rehab.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    private static final String PAGE = "page";
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
    public String getAllByPatientId(@PathVariable int patientId, Model model,
                                    @PageableDefault(value = 25) Pageable pageable) {
        model.addAttribute(PAGE, eventService.getAllByPatientId(patientId, pageable));
        return EVENTS_LIST;
    }

    @GetMapping("/nurse/{nurseId}")
    public String getAllByNurseId(@PathVariable int nurseId, Model model,
                                  @PageableDefault(value = 25) Pageable pageable) {
        model.addAttribute(PAGE, eventService.getAllByNurseId(nurseId, pageable));
        return EVENTS_LIST;
    }

    @GetMapping("/filter")
    public String filter(@RequestParam @Nullable
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate plannedDate,
                         @RequestParam @Nullable Integer insuranceNumber, Model model,
                         @PageableDefault(value = 25) Pageable pageable) {
        if ((plannedDate != null) && (insuranceNumber != null)) {
            model.addAttribute(PAGE, eventService.getAllByInsuranceNumberAndPlannedDate(insuranceNumber,
                    plannedDate, pageable));
        }
        if ((plannedDate == null) && (insuranceNumber != null)) {
            model.addAttribute(PAGE, eventService.getAllByInsuranceNumber(insuranceNumber, pageable));
        }
        if ((plannedDate != null) && (insuranceNumber == null)) {
            model.addAttribute(PAGE, eventService.getAllByPlannedDate(plannedDate, pageable));
        }
        if ((plannedDate == null) && (insuranceNumber == null)) {
            model.addAttribute(PAGE, eventService.getAll(pageable));
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
    public String events(@PageableDefault(value = 25) Pageable pageable, Model model) {
        model.addAttribute(PAGE, eventService.getAll(pageable));
        return EVENTS_LIST;
    }

    @GetMapping("/today")
    public String todayEvents(Model model) {
        return "redirect:/events/filter?plannedDate=" + LocalDate.now() + "&insuranceNumber=";
    }
}
