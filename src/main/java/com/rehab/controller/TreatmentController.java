package com.rehab.controller;

import com.rehab.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/treatments")
public class TreatmentController {

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
        model.addAttribute("treatments", treatmentService.getAllByPatientId(patientId));
        return "treatments/list";
    }

    @GetMapping(value = "/getBy", params = "doctorId")
    public String getAllByDoctorId(@RequestParam int doctorId, Model model) {
        model.addAttribute("treatments", treatmentService.getAllByDoctorId(doctorId));
        return "treatments/list";
    }

    @GetMapping
    public String treatments(Model model) {
        model.addAttribute("treatments", treatmentService.getAll());
        return "treatments/list";
    }
}
