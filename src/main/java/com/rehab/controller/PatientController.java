package com.rehab.controller;

import com.rehab.dto.PatientDto;
import com.rehab.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patients")
@Secured({"ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_NURSE"})
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        model.addAttribute("patient", patientService.getById(id));
        return "patients/patient";
    }

    @GetMapping("/insNum/{insuranceNumber}")
    public String getByInsuranceNumber(@PathVariable int insuranceNumber, Model model) {
        model.addAttribute("patient", patientService.getByInsuranceNumber(insuranceNumber));
        return "patients/patient";
    }

    @GetMapping("/discharge/{id}")
    public String discharge(@PathVariable int id, Model model) {
        patientService.discharge(id);
        return "redirect:/patients/" + id;
    }

    @GetMapping
    public String patients(Model model) {
        model.addAttribute("patients", patientService.getAll());
        return "patients/list";
    }

    @GetMapping("/new")
    @Secured("ROLE_DOCTOR")
    public String create() {
        return "patients/new";
    }

    @PostMapping("/new")
    @Secured("ROLE_DOCTOR")
    public String createPatient(PatientDto patientDto) {
        patientService.save(patientDto);
        return "redirect:";
    }
}
