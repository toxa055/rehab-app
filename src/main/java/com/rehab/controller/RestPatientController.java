package com.rehab.controller;

import com.rehab.dto.PatientDto;
import com.rehab.service.PatientService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for Patient.
 * It's used in some jsp pages, when we need to get or send data without page reload.
 * Any method returns execution result in JSON format in response body.
 */
@Secured("ROLE_DOCTOR")
@RestController
public class RestPatientController {

    /**
     * PatientService bean.
     */
    private final PatientService patientService;

    /**
     * Interface that logs specific messages.
     */
    private final Logger logger;

    /**
     * @param patientService description of patientService is in field declaration.
     * @param logger         description of logger is in field declaration.
     */
    @Autowired
    public RestPatientController(PatientService patientService, Logger logger) {
        this.patientService = patientService;
        this.logger = logger;
    }

    /**
     * Method executes GET request to receive patientDto by given insurance number.
     *
     * @param insNum patient insurance number.
     * @return found patientDto.
     */
    @GetMapping("/rest/patients/{insNum}")
    public PatientDto getByInsNum(@PathVariable int insNum) {
        logger.info("Get patient by insurance number {} through rest.", insNum);
        return patientService.getByInsuranceNumber(insNum);
    }
}
