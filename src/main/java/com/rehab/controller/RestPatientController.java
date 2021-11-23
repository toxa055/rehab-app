package com.rehab.controller;

import com.rehab.dto.PatientDto;
import com.rehab.service.PatientService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestPatientController {

    private final PatientService patientService;
    private final Logger logger;

    @Autowired
    public RestPatientController(PatientService patientService, Logger logger) {
        this.patientService = patientService;
        this.logger = logger;
    }

    @GetMapping("/rest/patients/{insNum}")
    public PatientDto getByInsNum(@PathVariable int insNum) {
        logger.info("Get patient by insurance number {} through rest.", insNum);
        return patientService.getByInsuranceNumber(insNum);
    }
}
