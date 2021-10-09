package com.rehab.controller;

import com.rehab.dto.PatientDto;
import com.rehab.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public String patients(Model model) {
        model.addAttribute("patients", patientService.getAll());
        return "patients/list";
    }

    @GetMapping("/new")
    public String create() {
        return "patients/new";
    }

    @PostMapping("/new")
    public String createPatient(PatientDto patientDto) {
        patientService.save(patientDto);
        return "redirect:";
    }
}
