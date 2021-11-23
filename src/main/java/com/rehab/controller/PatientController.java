package com.rehab.controller;

import com.rehab.dto.PatientDto;
import com.rehab.service.PatientService;
import com.rehab.util.ControllerUtil;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.lang.Nullable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/patients")
@Secured({"ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_NURSE"})
public class PatientController {

    private static final String CREATE_OR_UPDATE_PATIENT_URL = "patients/create_or_update";
    private static final String REDIRECT_PATIENTS_URL = "redirect:/patients/";
    private static final String PATIENT_URL = "patients/patient";
    private static final String PATIENT = "patient";
    private final PatientService patientService;
    private final Logger logger;

    @Autowired
    public PatientController(PatientService patientService, Logger logger) {
        this.patientService = patientService;
        this.logger = logger;
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        logger.info("Get patient by id {}.", id);
        model.addAttribute(PATIENT, patientService.getById(id));
        return PATIENT_URL;
    }

    @GetMapping("/insNum/{insuranceNumber}")
    public String getByInsuranceNumber(@PathVariable int insuranceNumber, Model model) {
        logger.info("Get patient by insurance number {}.", insuranceNumber);
        model.addAttribute(PATIENT, patientService.getByInsuranceNumber(insuranceNumber));
        return PATIENT_URL;
    }

    @GetMapping("/discharge/{id}")
    @Secured("ROLE_DOCTOR")
    public String discharge(@PathVariable int id) {
        logger.info("Discharge patient with id {}.", id);
        patientService.discharge(id);
        return REDIRECT_PATIENTS_URL + id;
    }

    @GetMapping
    public String patients() {
        return "redirect:/patients/filter";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam @Nullable Integer insuranceNumber,
                         @RequestParam @Nullable String nameLike,
                         @RequestParam @Nullable boolean onlyTreating,
                         @PageableDefault(value = 15, sort = "name") Pageable pageable,
                         Model model) {
        logger.info("Filter patients by insurance number {}, name like... {}, only treating {}.",
                insuranceNumber, nameLike, onlyTreating);
        model.addAttribute("page", patientService.filter(insuranceNumber, nameLike, onlyTreating, pageable));
        return "patients/list";
    }

    @GetMapping("/new")
    @Secured({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public String create() {
        return CREATE_OR_UPDATE_PATIENT_URL;
    }

    @GetMapping("/edit/{id}")
    @Secured({"ROLE_ADMIN"})
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute(PATIENT, patientService.getById(id));
        return CREATE_OR_UPDATE_PATIENT_URL;
    }

    @PostMapping("/new")
    @Secured({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public String createOrUpdate(@Valid PatientDto patientDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.warn("Binding result has errors: {}", bindingResult.getAllErrors());
            model.addAllAttributes(ControllerUtil.getErrorsMap(bindingResult));
            model.addAttribute(PATIENT, patientDto);
            return CREATE_OR_UPDATE_PATIENT_URL;
        }
        var isNull = patientDto.getId() == null;
        PatientDto patient;
        if (isNull) {
            logger.info("Create new patient.");
            patient = patientService.save(patientDto);
        } else {
            logger.info("Update patient with id {}.", patientDto.getId());
            patient = patientService.update(patientDto);
        }
        return REDIRECT_PATIENTS_URL + patient.getId();
    }
}
