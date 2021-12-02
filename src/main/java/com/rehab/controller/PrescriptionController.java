package com.rehab.controller;

import com.rehab.dto.PrescriptionDto;
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
 * Controller that executes http requests for Prescription.
 * Any method returns name of jsp page to show it to user or redirects to another.
 * Almost any method adds to model execution result of prescriptionService (as attribute).
 */
@Controller
@RequestMapping("/prescriptions")
@Secured({"ROLE_ADMIN", "ROLE_DOCTOR"})
public class PrescriptionController {

    /**
     * Name of jsp page that contains form for creating or editing prescription.
     */
    private static final String CREATE_OR_UPDATE_PRESCRIPTION_URL = "/prescriptions/create_or_update";

    /**
     * Redirection to page of prescriptions.
     */
    private static final String REDIRECT_PRESCRIPTIONS_URL = "redirect:/prescriptions/";

    /**
     * Name of jsp page that shows list of prescriptions.
     */
    private static final String PRESCRIPTIONS_URL = "/prescriptions/list";

    /**
     * Name of model attribute for Treatment.
     */
    private static final String TREATMENT = "treatment";

    /**
     * Name of model attribute for Prescription.
     */
    private static final String PRESCRIPTION = "p";

    /**
     * Name of model attribute for page content.
     */
    private static final String PAGE = "page";

    /**
     * PrescriptionService bean.
     */
    private final PrescriptionService prescriptionService;

    /**
     * TreatmentService bean.
     */
    private final TreatmentService treatmentService;

    /**
     * Interface that logs specific messages.
     */
    private final Logger logger;

    /**
     * Constructs new instance and initializes following fields.
     *
     * @param prescriptionService description of prescriptionService is in field declaration.
     * @param treatmentService    description of treatmentService is in field declaration.
     * @param logger              description of logger is in field declaration.
     */
    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService, TreatmentService treatmentService,
                                  Logger logger) {
        this.prescriptionService = prescriptionService;
        this.treatmentService = treatmentService;
        this.logger = logger;
    }

    /**
     * Method executes GET request to receive prescription by given id.
     *
     * @param id    prescription id.
     * @param model holder for model attributes.
     * @return name of jsp page.
     */
    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        logger.info("Get prescription by id {}.", id);
        model.addAttribute(PRESCRIPTION, prescriptionService.getPrescriptionDtoOutById(id));
        model.addAttribute("authDoctorId", SecurityUtil.getAuthEmployee().getId());
        return "prescriptions/prescription";
    }

    /**
     * Method executes GET request to receive list of prescriptions for particular treatment by given treatment id.
     *
     * @param treatmentId treatment id.
     * @param pageable    interface that provides pagination.
     * @param model       holder for model attributes.
     * @return name of jsp page.
     */
    @GetMapping("/treatment/{treatmentId}")
    public String getByTreatmentId(@PathVariable int treatmentId,
                                   @PageableDefault(value = 15, sort = "date") Pageable pageable, Model model) {
        logger.info("Get prescriptions by treatment id {}.", treatmentId);
        model.addAttribute(PAGE, prescriptionService.getByTreatmentId(treatmentId, pageable));
        return PRESCRIPTIONS_URL;
    }

    /**
     * Method executes GET request to receive list of prescriptions filtered by given parameters.
     *
     * @param pDate           particular date when prescriptions were created.
     * @param insuranceNumber patient insurance number.
     * @param authDoctor      only prescriptions that were created by authenticated doctor or any.
     * @param onlyActive      only active prescriptions or any.
     * @param pageable        interface that provides pagination.
     * @param model           holder for model attributes.
     * @return name of jsp page.
     */
    @GetMapping("/filter")
    public String filter(@RequestParam @Nullable
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate pDate,
                         @RequestParam @Nullable Integer insuranceNumber,
                         @RequestParam @Nullable boolean authDoctor,
                         @RequestParam @Nullable boolean onlyActive,
                         @PageableDefault(value = 15, sort = "date") Pageable pageable,
                         Model model) {
        logger.info("Filter prescriptions by date {}, insurance number {}, authenticated doctor {}, only active treatments {}.",
                pDate, insuranceNumber, authDoctor, onlyActive);
        model.addAttribute(PAGE, prescriptionService.filter(pDate, insuranceNumber, authDoctor, onlyActive, pageable));
        return PRESCRIPTIONS_URL;
    }

    /**
     * Method executes GET request to receive list of prescriptions filtered by today date
     * and created by only authenticated doctor.
     *
     * @return redirect to list of prescriptions.
     */
    @GetMapping("/today")
    public String today() {
        return "redirect:/prescriptions/filter?pDate=" + LocalDate.now() + "&insuranceNumber=&authDoctor=on";
    }

    /**
     * Method executes GET request to receive list of prescriptions with no filter.
     *
     * @return redirect to list of prescriptions.
     */
    @GetMapping
    public String prescriptions() {
        return "redirect:/prescriptions/filter?pDate=&insuranceNumber=";
    }

    /**
     * Method executes GET request to open jsp page where new prescription
     * (based on given treatment id) will be created.
     *
     * @param treatmentId treatment id.
     * @param model       holder for model attributes.
     * @return name of jsp page.
     */
    @GetMapping("/new/{treatmentId}")
    @Secured("ROLE_DOCTOR")
    public String create(@PathVariable int treatmentId, Model model) {
        model.addAttribute(TREATMENT, treatmentService.getById(treatmentId));
        return CREATE_OR_UPDATE_PRESCRIPTION_URL;
    }

    /**
     * Method executes POST request to create new prescription. Given prescriptionDto is validated before saving.
     *
     * @param prescriptionDto that will be saved.
     * @param bindingResult   holder for field errors.
     * @param model           holder for model attributes.
     * @return redirect to created prescription.
     */
    @PostMapping("/new")
    @Secured("ROLE_DOCTOR")
    public String createPrescription(@Valid PrescriptionDto prescriptionDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.warn("Binding result for prescription has errors: {}", bindingResult.getAllErrors());
            return addAttributes(prescriptionDto, bindingResult, model);
        }
        logger.info("Create new prescription for patient with id {}.", prescriptionDto.getPatientId());
        var savedPrescription = prescriptionService.save(prescriptionDto);
        return REDIRECT_PRESCRIPTIONS_URL + savedPrescription.getId();
    }

    /**
     * Method executes GET request to open jsp page where new prescription
     * (based on given treatment id and prescription id) will be created.
     *
     * @param treatmentId    treatment id.
     * @param prescriptionId prescription id.
     * @param model          holder for model attributes.
     * @return name of jsp page.
     */
    @GetMapping("/edit/{treatmentId}/{prescriptionId}")
    @Secured("ROLE_DOCTOR")
    public String update(@PathVariable int treatmentId, @PathVariable int prescriptionId, Model model) {
        model.addAttribute(TREATMENT, treatmentService.getById(treatmentId));
        model.addAttribute(PRESCRIPTION, prescriptionService.getById(prescriptionId));
        return CREATE_OR_UPDATE_PRESCRIPTION_URL;
    }

    /**
     * Method executes POST request to create new prescription (based on the previous one).
     * Given prescriptionDto is validated before saving.
     *
     * @param prescriptionDto that will be saved.
     * @param bindingResult   holder for field errors.
     * @param model           holder for model attributes.
     * @return redirect to created prescription.
     */
    @PostMapping("/edit")
    @Secured("ROLE_DOCTOR")
    public String updatePrescription(@Valid PrescriptionDto prescriptionDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.warn("Binding result for prescription has errors: {}", bindingResult.getAllErrors());
            return addAttributes(prescriptionDto, bindingResult, model);
        }
        logger.info("Update prescription with id {} for patient with id {}.",
                prescriptionDto.getId(), prescriptionDto.getPatientId());
        var savedPrescription = prescriptionService.update(prescriptionDto);
        return REDIRECT_PRESCRIPTIONS_URL + savedPrescription.getId();
    }

    /**
     * Method executes GET request to cancel prescription by given id.
     *
     * @param id    prescription id.
     * @param model holder for model attributes.
     * @return redirect to current prescription.
     */
    @GetMapping("/cancel/{id}")
    @Secured("ROLE_DOCTOR")
    public String cancel(@PathVariable int id, Model model) {
        logger.info("Cancel prescription with id {}.", id);
        model.addAttribute(PRESCRIPTION, prescriptionService.cancel(id));
        return REDIRECT_PRESCRIPTIONS_URL + id;
    }

    /**
     * Method executes GET request to close prescription by given id.
     *
     * @param id    prescription id.
     * @param model holder for model attributes.
     * @return redirect to current prescription.
     */
    @GetMapping("/close/{id}")
    @Secured("ROLE_DOCTOR")
    public String close(@PathVariable int id, Model model) {
        logger.info("Close prescription with id {}.", id);
        model.addAttribute(PRESCRIPTION, prescriptionService.close(id));
        return REDIRECT_PRESCRIPTIONS_URL + id;
    }

    /**
     * Method adds attributes (field errors map, prescriptionDto, treatmentDto) to model.
     *
     * @param dto    prescriptionDto that will be added to model.
     * @param result holder for field errors.
     * @param model  holder for model attributes.
     * @return name of jsp page.
     */
    private String addAttributes(PrescriptionDto dto, BindingResult result, Model model) {
        model.addAllAttributes(ControllerUtil.getErrorsMap(result));
        model.addAttribute(PRESCRIPTION, dto);
        model.addAttribute(TREATMENT, treatmentService.getById(dto.getTreatmentId()));
        return CREATE_OR_UPDATE_PRESCRIPTION_URL;
    }
}
