package com.rehab.controller;

import com.rehab.dto.TreatmentDto;
import com.rehab.service.PatientService;
import com.rehab.service.TreatmentService;
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
    private final TreatmentService treatmentService;
    private final PatientService patientService;

    @Autowired
    public TreatmentController(TreatmentService treatmentService, PatientService patientService) {
        this.treatmentService = treatmentService;
        this.patientService = patientService;
    }

    @GetMapping(value = "/getBy", params = "id")
    public String getById(@RequestParam int id, Model model) {
        model.addAttribute("treatment", treatmentService.getById(id));
        return "treatments/treatment";
    }

    @GetMapping(value = "/getBy", params = "patientId")
    public String getAllByPatientId(@RequestParam int patientId, Model model) {
        model.addAttribute(TREATMENTS, treatmentService.getAllByPatientId(patientId));
        return TREATMENTS_LIST;
    }

    @GetMapping(value = "/getBy", params = "doctorId")
    public String getAllByDoctorId(@RequestParam int doctorId, Model model) {
        model.addAttribute(TREATMENTS, treatmentService.getAllByDoctorId(doctorId));
        return TREATMENTS_LIST;
    }

    @GetMapping
    public String treatments(Model model) {
        model.addAttribute(TREATMENTS, treatmentService.getAll());
        return TREATMENTS_LIST;
    }

    @GetMapping("/new/{patientId}")
    @Secured("ROLE_DOCTOR")
    public String create(@PathVariable int patientId, Model model) {
        model.addAttribute("patient", patientService.getById(patientId));
        return "treatments/new";
    }

    @PostMapping("/new")
    @Secured("ROLE_DOCTOR")
    public String createTreatment(TreatmentDto treatmentDto) {
        treatmentService.save(treatmentDto);
        return "redirect:..";
    }
}
