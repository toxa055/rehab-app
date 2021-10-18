package com.rehab.controller;

import com.rehab.dto.PatientDto;
import com.rehab.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestPatientController {

    private final PatientService patientService;

    @Autowired
    public RestPatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/rest/patients/{insNum}")
    public PatientDto getByInsNum(@PathVariable int insNum) {
        return patientService.getByInsuranceNumber(insNum);
    }
}
