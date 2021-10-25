package com.rehab.controller;

import com.rehab.dto.TreatmentDto;
import com.rehab.service.PatientService;
import com.rehab.service.TreatmentService;
import com.rehab.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/treatments")
@Secured({"ROLE_ADMIN", "ROLE_DOCTOR"})
public class TreatmentController {

    private static final String TREATMENTS = "treatments";
    private static final String TREATMENTS_LIST = "/treatments/list";
    private static final String NEW_TREATMENT = "/treatments/new";
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

    @GetMapping("/patient/{patientId}")
    public String getAllByPatientId(@PathVariable int patientId, Model model) {
        model.addAttribute(TREATMENTS, treatmentService.getAllByPatientId(patientId));
        return TREATMENTS_LIST;
    }

    @GetMapping("/doctor/{doctorId}")
    public String getAllByDoctorId(@PathVariable int doctorId, Model model) {
        model.addAttribute(TREATMENTS, treatmentService.getAllByDoctorId(doctorId));
        return TREATMENTS_LIST;
    }

    @GetMapping
    public String treatments(Model model) {
        model.addAttribute(TREATMENTS, treatmentService.getAll());
        return TREATMENTS_LIST;
    }

    @GetMapping("/close/{id}")
    @Secured("ROLE_DOCTOR")
    public String close(@PathVariable int id, Model model) {
        treatmentService.close(id);
        return "redirect:../" + id;
    }

    @GetMapping("/new")
    @Secured("ROLE_DOCTOR")
    public String create(Model model) {
        return NEW_TREATMENT;
    }

    @GetMapping("/new/{patientId}")
    @Secured("ROLE_DOCTOR")
    public String create(@PathVariable int patientId, Model model) {
        model.addAttribute("patient", patientService.getById(patientId));
        return NEW_TREATMENT;
    }

    @PostMapping("/new")
    @Secured("ROLE_DOCTOR")
    public String createTreatment(TreatmentDto treatmentDto) {
        treatmentService.save(treatmentDto);
        return "redirect:..";
    }
}
