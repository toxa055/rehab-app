package com.rehab.controller;

import com.rehab.service.EventService;
import com.rehab.util.SecurityUtil;
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
    private static final String REDIRECT = "redirect:../";
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{eventId}")
    public String getById(@PathVariable int eventId, Model model) {
        model.addAttribute("event", eventService.getById(eventId));
        model.addAttribute("authNurseId", SecurityUtil.getAuthEmployee().getId());
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
                         @RequestParam @Nullable Integer insuranceNumber,
                         @RequestParam @Nullable boolean authNurse,
                         @RequestParam @Nullable boolean onlyPlanned,
                         Model model, @PageableDefault(value = 25) Pageable pageable) {
        model.addAttribute(PAGE, eventService.filter(plannedDate, insuranceNumber, authNurse, onlyPlanned, pageable));
        return EVENTS_LIST;
    }

    @Secured("ROLE_NURSE")
    @GetMapping("/choose/{eventId}")
    public String choose(@PathVariable int eventId) {
        eventService.setNurse(eventId);
        return REDIRECT + eventId;
    }

    @Secured("ROLE_NURSE")
    @GetMapping("/discard/{eventId}")
    public String discard(@PathVariable int eventId) {
        eventService.unSetNurse(eventId);
        return REDIRECT + eventId;
    }

    @Secured("ROLE_NURSE")
    @GetMapping("/change/{eventId}")
    public String changeState(@PathVariable int eventId, @RequestParam String state,
                              @RequestParam @Nullable String comment) {
        eventService.changeStatus(eventId, state, comment);
        return REDIRECT + eventId;
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
