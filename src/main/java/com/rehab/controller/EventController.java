package com.rehab.controller;

import com.rehab.service.EventService;
import com.rehab.util.SecurityUtil;
import org.apache.logging.log4j.Logger;
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

/**
 * Controller that executes http requests for Event.
 * Any method returns name of jsp page to show it to user or redirects to another.
 * Almost any method adds to model execution result of eventService (as attribute).
 */
@Controller
@RequestMapping("/events")
@Secured({"ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_NURSE"})
public class EventController {

    /**
     * Name of model attribute for page content.
     */
    private static final String PAGE = "page";

    /**
     * Redirection to previous resource.
     */
    private static final String REDIRECT = "redirect:../";

    /**
     * Name of jsp page that shows list of events.
     */
    private static final String EVENTS_URL = "/events/list";

    /**
     * EventService bean.
     */
    private final EventService eventService;

    /**
     * Interface that logs specific messages.
     */
    private final Logger logger;

    /**
     * Constructs new instance and initializes following fields.
     *
     * @param eventService description of eventService is in field declaration.
     * @param logger       description of logger is in field declaration.
     */
    @Autowired
    public EventController(EventService eventService, Logger logger) {
        this.eventService = eventService;
        this.logger = logger;
    }

    /**
     * Method executes GET request to receive event by given id.
     *
     * @param eventId event id.
     * @param model   holder for model attributes.
     * @return name of jsp page.
     */
    @GetMapping("/{eventId}")
    public String getById(@PathVariable int eventId, Model model) {
        logger.info("Get event by id {}.", eventId);
        model.addAttribute("event", eventService.getById(eventId));
        model.addAttribute("authNurseId", SecurityUtil.getAuthEmployee().getId());
        return "/events/event";
    }

    /**
     * Method executes GET request to receive list of events for particular prescription by given prescription id.
     *
     * @param prescriptionId prescription id.
     * @param pageable       interface that provides pagination.
     * @param model          holder for model attributes.
     * @return name of jsp page.
     */
    @GetMapping("/prescription/{prescriptionId}")
    public String getByPrescriptionId(@PathVariable int prescriptionId,
                                      @PageableDefault(value = 25, sort = {"plannedDate", "plannedTime"})
                                              Pageable pageable, Model model) {
        logger.info("Get events by prescription id {}.", prescriptionId);
        model.addAttribute(PAGE, eventService.getByPrescriptionId(prescriptionId, pageable));
        return EVENTS_URL;
    }

    /**
     * Method executes GET request to receive list of events filtered by given parameters.
     *
     * @param plannedDate     particular date when events are planned for.
     * @param insuranceNumber patient insurance number.
     * @param authNurse       only events that authenticated nurse is a performer or any.
     * @param onlyPlanned     only planned events or any.
     * @param pageable        interface that provides pagination.
     * @param model           holder for model attributes.
     * @return name of jsp page.
     */
    @GetMapping("/filter")
    public String filter(@RequestParam @Nullable
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate plannedDate,
                         @RequestParam @Nullable Integer insuranceNumber,
                         @RequestParam @Nullable boolean authNurse,
                         @RequestParam @Nullable boolean onlyPlanned,
                         @PageableDefault(value = 25, sort = {"plannedDate", "plannedTime"}) Pageable pageable,
                         Model model) {
        logger.info("Filter events by planned date {}, insurance number {}, authenticated nurse {}, only planned events {}.",
                plannedDate, insuranceNumber, authNurse, onlyPlanned);
        model.addAttribute(PAGE, eventService.filter(plannedDate, insuranceNumber, authNurse, onlyPlanned, pageable));
        return EVENTS_URL;
    }

    /**
     * Method executes GET request to choose event for authenticated nurse by given id.
     *
     * @param eventId event id.
     * @return redirect to current event.
     */
    @Secured("ROLE_NURSE")
    @GetMapping("/choose/{eventId}")
    public String choose(@PathVariable int eventId) {
        logger.info("Choose event with id {}.", eventId);
        eventService.setNurse(eventId);
        return REDIRECT + eventId;
    }

    /**
     * Method executes GET request to discard event for authenticated nurse by given id.
     *
     * @param eventId event id.
     * @return redirect to current event.
     */
    @Secured("ROLE_NURSE")
    @GetMapping("/discard/{eventId}")
    public String discard(@PathVariable int eventId) {
        logger.info("Discard event with id {}.", eventId);
        eventService.unSetNurse(eventId);
        return REDIRECT + eventId;
    }

    /**
     * Method executes GET request to change state for event.
     *
     * @param eventId event id.
     * @param state   state that will be set for event.
     * @param comment string comment that will be added to event.
     * @return redirect to current event.
     */
    @Secured("ROLE_NURSE")
    @GetMapping("/change/{eventId}")
    public String changeState(@PathVariable int eventId, @RequestParam String state,
                              @RequestParam @Nullable String comment) {
        logger.info("Change event state on {} for event with id {}.", state, eventId);
        eventService.changeState(eventId, state, comment);
        return REDIRECT + eventId;
    }

    /**
     * Method executes GET request to receive list of events with no filter.
     *
     * @return redirect to list of events.
     */
    @GetMapping
    public String events() {
        return "redirect:/events/filter?plannedDate=&insuranceNumber=";
    }

    /**
     * Method executes GET request to receive list of events filtered by today date.
     *
     * @return redirect to list of events.
     */
    @GetMapping("/today")
    public String todayEvents() {
        return "redirect:/events/filter?plannedDate=" + LocalDate.now() + "&insuranceNumber=";
    }
}
