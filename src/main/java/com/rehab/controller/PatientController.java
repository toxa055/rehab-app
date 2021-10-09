package com.rehab.controller;

import com.rehab.dto.PatientDto;
import com.rehab.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping(value = "/getBy", params = "id")
    public String getById(@RequestParam int id, Model model) {
        model.addAttribute("patient", patientService.getById(id));
        return "patients/patient";
    }

    @GetMapping(value = "/getBy", params = "insuranceNumber")
    public String getByInsuranceNumber(int insuranceNumber, Model model) {
        model.addAttribute("patient", patientService.getByInsuranceNumber(insuranceNumber));
        return "patients/patient";
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
