package com.rehab.controller;

import com.rehab.dto.PatientDto;
import com.rehab.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/patients")
@Secured({"ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_NURSE"})
public class PatientController {

    private static final String NEW_PATIENT_URL = "patients/new";
    private static final String PATIENT_URL = "patients/patient";
    private static final String PATIENT = "patient";
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        model.addAttribute(PATIENT, patientService.getById(id));
        return PATIENT_URL;
    }

    @GetMapping("/insNum/{insuranceNumber}")
    public String getByInsuranceNumber(@PathVariable int insuranceNumber, Model model) {
        model.addAttribute(PATIENT, patientService.getByInsuranceNumber(insuranceNumber));
        return PATIENT_URL;
    }

    @GetMapping("/discharge/{id}")
    public String discharge(@PathVariable int id, Model model) {
        patientService.discharge(id);
        return "redirect:/patients/" + id;
    }

    @GetMapping
    public String patients(@PageableDefault(15) Pageable pageable, Model model) {
        model.addAttribute("page", patientService.getAll(pageable));
        return "patients/list";
    }

    @GetMapping("/new")
    @Secured("ROLE_DOCTOR")
    public String create() {
        return NEW_PATIENT_URL;
    }

    @PostMapping("/new")
    @Secured("ROLE_DOCTOR")
    public String createPatient(@Valid PatientDto patientDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            var errorsMap = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(f -> f.getField() + "Error", FieldError::getDefaultMessage,
                            (m1, m2) -> String.join("<br>", m1, m2)));
            model.addAllAttributes(errorsMap);
            model.addAttribute("name", patientDto.getName());
            model.addAttribute("insuranceNumber", patientDto.getInsuranceNumber());
            model.addAttribute("address", patientDto.getAddress());
            return NEW_PATIENT_URL;
        }
        patientService.save(patientDto);
        return "redirect:";
    }
}
