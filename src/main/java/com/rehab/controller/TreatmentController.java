package com.rehab.controller;

import com.rehab.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/treatments")
public class TreatmentController {

    private static final String TREATMENTS = "treatments";
    private static final String TREATMENTS_LIST = "/treatments/list";
    private final TreatmentService treatmentService;

    @Autowired
    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
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
}
