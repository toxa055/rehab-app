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

/**
 * Controller that executes http requests for Patient.
 * Any method returns name of jsp page to show it to user or redirects to another.
 * Almost any method adds to model execution result of patientService (as attribute).
 */
@Controller
@RequestMapping("/patients")
@Secured({"ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_NURSE"})
public class PatientController {

    /**
     * Name of jsp page that contains form for creating or editing patient.
     */
    private static final String CREATE_OR_UPDATE_PATIENT_URL = "patients/create_or_update";

    /**
     * Redirection to page of patients.
     */
    private static final String REDIRECT_PATIENTS_URL = "redirect:/patients/";

    /**
     * Name of jsp page that shows only one patient.
     */
    private static final String PATIENT_URL = "patients/patient";

    /**
     * Name of model attribute for Patient.
     */
    private static final String PATIENT = "patient";

    /**
     * PatientService bean.
     */
    private final PatientService patientService;

    /**
     * Interface that logs specific messages.
     */
    private final Logger logger;

    /**
     * Constructs new instance and initializes following fields.
     *
     * @param patientService description of patientService is in field declaration.
     * @param logger         description of logger is in field declaration.
     */
    @Autowired
    public PatientController(PatientService patientService, Logger logger) {
        this.patientService = patientService;
        this.logger = logger;
    }

    /**
     * Method executes GET request to receive patient by given id.
     *
     * @param id    patient id.
     * @param model holder for model attributes.
     * @return name of jsp page.
     */
    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        logger.info("Get patient by id {}.", id);
        model.addAttribute(PATIENT, patientService.getById(id));
        return PATIENT_URL;
    }

    /**
     * Method executes GET request to receive patient by given insurance number.
     *
     * @param insuranceNumber patient insurance number.
     * @param model           holder for model attributes.
     * @return name of jsp page.
     */
    @GetMapping("/insNum/{insuranceNumber}")
    public String getByInsuranceNumber(@PathVariable int insuranceNumber, Model model) {
        logger.info("Get patient by insurance number {}.", insuranceNumber);
        model.addAttribute(PATIENT, patientService.getByInsuranceNumber(insuranceNumber));
        return PATIENT_URL;
    }

    /**
     * Method executes GET request to discharge patient by given id.
     *
     * @param id patient id.
     * @return redirect to current patient.
     */
    @GetMapping("/discharge/{id}")
    @Secured("ROLE_DOCTOR")
    public String discharge(@PathVariable int id) {
        logger.info("Discharge patient with id {}.", id);
        patientService.discharge(id);
        return REDIRECT_PATIENTS_URL + id;
    }

    /**
     * Method executes GET request to receive list of patients with no filter.
     *
     * @return redirect to list of patients.
     */
    @GetMapping
    public String patients() {
        return "redirect:/patients/filter";
    }

    /**
     * Method executes GET request to receive list of patients filtered by given parameters.
     *
     * @param insuranceNumber patient insurance number.
     * @param nameLike        not a particular patient name, but char sequence which patient names have to contain.
     * @param onlyTreating    patient state: only 'TREATING' or any.
     * @param pageable        interface that provides pagination.
     * @param model           holder for model attributes.
     * @return name of jsp page.
     */
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

    /**
     * Method executes GET request to open jsp page where new patient will be created.
     *
     * @return name of jsp page.
     */
    @GetMapping("/new")
    @Secured({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public String create() {
        return CREATE_OR_UPDATE_PATIENT_URL;
    }

    /**
     * Method executes GET request to open jsp page where patient (by given id) will be edited.
     *
     * @param id    patient id.
     * @param model holder for model attributes.
     * @return @return name of jsp page.
     */
    @GetMapping("/edit/{id}")
    @Secured({"ROLE_ADMIN"})
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute(PATIENT, patientService.getById(id));
        return CREATE_OR_UPDATE_PATIENT_URL;
    }

    /**
     * Method executes POST request to create new patient or update existing one.
     * Given patientDto is validated before saving.
     *
     * @param patientDto    that will be created (if it's new) or updated.
     * @param bindingResult holder for field errors.
     * @param model         holder for model attributes.
     * @return redirect to current patient.
     */
    @PostMapping("/new")
    @Secured({"ROLE_ADMIN", "ROLE_DOCTOR"})
    public String createOrUpdate(@Valid PatientDto patientDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.warn("Binding result for patient has errors: {}", bindingResult.getAllErrors());
            model.addAllAttributes(ControllerUtil.getErrorsMap(bindingResult));
            model.addAttribute(PATIENT, patientDto);
            return CREATE_OR_UPDATE_PATIENT_URL;
        }
        PatientDto patient;
        if (patientDto.getId() == null) {
            logger.info("Create new patient.");
            patient = patientService.save(patientDto);
        } else {
            logger.info("Update patient with id {}.", patientDto.getId());
            patient = patientService.update(patientDto);
        }
        return REDIRECT_PATIENTS_URL + patient.getId();
    }
}
