package com.rehab.controller;

import com.rehab.dto.TreatmentDto;
import com.rehab.service.PatientService;
import com.rehab.service.TreatmentService;
import com.rehab.util.ControllerUtil;
import com.rehab.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("/treatments")
@Secured({"ROLE_ADMIN", "ROLE_DOCTOR"})
public class TreatmentController {

    private static final String NEW_TREATMENT_URL = "/treatments/new";
    private static final String REDIRECT = "redirect:../";
    private final TreatmentService treatmentService;
    private final PatientService patientService;

    @Autowired
    public TreatmentController(TreatmentService treatmentService, PatientService patientService) {
        this.treatmentService = treatmentService;
        this.patientService = patientService;
    }

    @GetMapping(value = "/{id}")
    public String getById(@PathVariable int id, Model model) {
        model.addAttribute("treatment", treatmentService.getById(id));
        model.addAttribute("authDoctorId", SecurityUtil.getAuthEmployee().getId());
        return "treatments/treatment";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam @Nullable
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tDate,
                         @RequestParam @Nullable Integer insuranceNumber,
                         @RequestParam @Nullable boolean authDoctor,
                         @RequestParam @Nullable boolean onlyOpen,
                         @PageableDefault(value = 15, sort = "date") Pageable pageable,
                         Model model) {
        model.addAttribute("page", treatmentService.filter(tDate, insuranceNumber, authDoctor,
                onlyOpen, pageable));
        return "/treatments/list";
    }

    @GetMapping
    public String treatments(Model model) {
        return "redirect:/treatments/filter?tDate=&insuranceNumber=";
    }

    @GetMapping("/today")
    public String todayTreatments(Model model) {
        return "redirect:/treatments/filter?tDate=" + LocalDate.now() + "&insuranceNumber=&authDoctor=on";
    }

    @GetMapping("/close/{id}")
    @Secured("ROLE_DOCTOR")
    public String close(@PathVariable int id, Model model) {
        treatmentService.close(id);
        return REDIRECT + id;
    }

    @GetMapping("/new")
    @Secured("ROLE_DOCTOR")
    public String create(Model model) {
        return NEW_TREATMENT_URL;
    }

    @GetMapping("/new/{patientId}")
    @Secured("ROLE_DOCTOR")
    public String create(@PathVariable int patientId, Model model) {
        model.addAttribute("patient", patientService.getById(patientId));
        return NEW_TREATMENT_URL;
    }

    @PostMapping("/new")
    @Secured("ROLE_DOCTOR")
    public String createTreatment(@Valid TreatmentDto treatmentDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAllAttributes(ControllerUtil.getErrorsMap(bindingResult));
            model.addAttribute("t", treatmentDto);
            return NEW_TREATMENT_URL;
        }
        var savedTreatment = treatmentService.save(treatmentDto);
        return REDIRECT + savedTreatment.getId();
    }
}
