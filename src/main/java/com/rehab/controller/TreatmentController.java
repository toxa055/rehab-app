package com.rehab.controller;

import com.rehab.dto.TreatmentDto;
import com.rehab.service.PatientService;
import com.rehab.service.PrescriptionService;
import com.rehab.service.TreatmentService;
import com.rehab.util.ControllerUtil;
import com.rehab.util.SecurityUtil;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

/**
 * Controller that executes http requests for Treatment.
 * Any method returns name of jsp page to show it to user or redirects to another.
 * Almost any method adds to model execution result of treatmentService (as attribute).
 */
@Controller
@RequestMapping("/treatments")
@Secured({"ROLE_ADMIN", "ROLE_DOCTOR"})
public class TreatmentController {

    /**
     * Name of jsp page that contains form for creating treatment.
     */
    private static final String NEW_TREATMENT_URL = "/treatments/new";

    /**
     * Redirection to previous resource.
     */
    private static final String REDIRECT = "redirect:../";

    /**
     * TreatmentService bean.
     */
    private final TreatmentService treatmentService;

    /**
     * PatientService bean.
     */
    private final PatientService patientService;

    /**
     * PrescriptionService bean.
     */
    private final PrescriptionService prescriptionService;

    /**
     * Interface that logs specific messages.
     */
    private final Logger logger;

    /**
     * Constructs new instance and initializes following fields.
     *
     * @param treatmentService    description of treatmentService is in field declaration.
     * @param patientService      description of patientService is in field declaration.
     * @param prescriptionService description of prescriptionService is in field declaration.
     * @param logger              description of logger is in field declaration.
     */
    @Autowired
    public TreatmentController(TreatmentService treatmentService, PatientService patientService,
                               PrescriptionService prescriptionService, Logger logger) {
        this.treatmentService = treatmentService;
        this.patientService = patientService;
        this.prescriptionService = prescriptionService;
        this.logger = logger;
    }

    /**
     * Method executes GET request to receive treatment by given id.
     *
     * @param id    treatment id.
     * @param model holder for model attributes.
     * @return name of jsp page.
     */
    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        logger.info("Get treatment by id {}.", id);
        model.addAttribute("treatment", treatmentService.getById(id));
        model.addAttribute("authDoctorId", SecurityUtil.getAuthEmployee().getId());
        model.addAllAttributes(prescriptionService.getPrescriptionsCountByTreatmentId(id));
        return "treatments/treatment";
    }

    /**
     * Method executes GET request to receive list of treatments filtered by given parameters.
     *
     * @param tDate           particular date when treatments were created.
     * @param insuranceNumber patient insurance number.
     * @param authDoctor      only treatments that were created by authenticated doctor or any.
     * @param onlyOpen        only open treatments or any.
     * @param pageable        interface that provides pagination.
     * @param model           holder for model attributes.
     * @return name of jsp page.
     */
    @GetMapping("/filter")
    public String filter(@RequestParam @Nullable
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tDate,
                         @RequestParam @Nullable Integer insuranceNumber,
                         @RequestParam @Nullable boolean authDoctor,
                         @RequestParam @Nullable boolean onlyOpen,
                         @PageableDefault(value = 15, sort = "date") Pageable pageable,
                         Model model) {
        logger.info("Filter treatments by date {}, insurance number {}, authenticated doctor {}, only open treatments {}.",
                tDate, insuranceNumber, authDoctor, onlyOpen);
        model.addAttribute("page", treatmentService.filter(tDate, insuranceNumber, authDoctor,
                onlyOpen, pageable));
        return "/treatments/list";
    }

    /**
     * Method executes GET request to receive list of treatments with no filter.
     *
     * @return redirect to list of treatments.
     */
    @GetMapping
    public String treatments() {
        return "redirect:/treatments/filter?tDate=&insuranceNumber=";
    }

    /**
     * Method executes GET request to receive list of treatments filtered by today date
     * and created by only authenticated doctor.
     *
     * @return redirect to list of treatments.
     */
    @GetMapping("/today")
    public String todayTreatments() {
        return "redirect:/treatments/filter?tDate=" + LocalDate.now() + "&insuranceNumber=&authDoctor=on";
    }

    /**
     * Method executes GET request to close treatment by given id.
     *
     * @param id treatment id.
     * @return redirect to current treatment.
     */
    @GetMapping("/close/{id}")
    @Secured("ROLE_DOCTOR")
    public String close(@PathVariable int id) {
        logger.info("Close treatment with id {}.", id);
        treatmentService.close(id);
        return REDIRECT + id;
    }

    /**
     * Method executes GET request to open jsp page where new treatment will be created.
     *
     * @return name of jsp page.
     */
    @GetMapping("/new")
    @Secured("ROLE_DOCTOR")
    public String create() {
        return NEW_TREATMENT_URL;
    }

    /**
     * Method executes GET request to open jsp page where new treatment
     * (for patient by given patient id) will be created.
     *
     * @param patientId patient id.
     * @param model     holder for model attributes.
     * @return name of jsp page.
     */
    @GetMapping("/new/{patientId}")
    @Secured("ROLE_DOCTOR")
    public String create(@PathVariable int patientId, Model model) {
        model.addAttribute("patient", patientService.getById(patientId));
        return NEW_TREATMENT_URL;
    }

    /**
     * Method executes POST request to create new treatment. Given treatmentDto is validated before saving.
     *
     * @param treatmentDto  that will be saved.
     * @param bindingResult holder for field errors.
     * @param model         holder for model attributes.
     * @return redirect to created treatment.
     */
    @PostMapping("/new")
    @Secured("ROLE_DOCTOR")
    public String createTreatment(@Valid TreatmentDto treatmentDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.warn("Binding result for treatment has errors: {}", bindingResult.getAllErrors());
            model.addAllAttributes(ControllerUtil.getErrorsMap(bindingResult));
            model.addAttribute("t", treatmentDto);
            return NEW_TREATMENT_URL;
        }
        logger.info("Create new treatment for patient with id {}.", treatmentDto.getPatientId());
        var savedTreatment = treatmentService.save(treatmentDto);
        return REDIRECT + savedTreatment.getId();
    }
}
