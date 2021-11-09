package com.rehab.controller;

import com.rehab.dto.PrescriptionDto;
import com.rehab.service.PrescriptionService;
import com.rehab.service.TreatmentService;
import com.rehab.util.ControllerUtil;
import com.rehab.util.SecurityUtil;
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

@Controller
@RequestMapping("/prescriptions")
@Secured({"ROLE_ADMIN", "ROLE_DOCTOR"})
public class PrescriptionController {

    private static final String CREATE_OR_UPDATE_PRESCRIPTION_URL = "/prescriptions/create_or_update";
    private static final String REDIRECT_PRESCRIPTIONS_URL = "redirect:/prescriptions/";
    private static final String PRESCRIPTIONS_URL = "/prescriptions/list";
    private static final String TREATMENT = "treatment";
    private static final String PRESCRIPTION = "p";
    private static final String PAGE = "page";
    private final PrescriptionService prescriptionService;
    private final TreatmentService treatmentService;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService, TreatmentService treatmentService) {
        this.prescriptionService = prescriptionService;
        this.treatmentService = treatmentService;
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        model.addAttribute(PRESCRIPTION, prescriptionService.getById(id));
        model.addAttribute("authDoctorId", SecurityUtil.getAuthEmployee().getId());
        return "prescriptions/prescription";
    }

    @GetMapping("/treatment/{treatmentId}")
    public String getByTreatmentId(@PathVariable int treatmentId,
                                   @PageableDefault(value = 15, sort = "date") Pageable pageable, Model model) {
        model.addAttribute(PAGE, prescriptionService.getByTreatmentId(treatmentId, pageable));
        return PRESCRIPTIONS_URL;
    }

    @GetMapping("/filter")
    public String filter(@RequestParam @Nullable
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate pDate,
                         @RequestParam @Nullable Integer insuranceNumber,
                         @RequestParam @Nullable boolean authDoctor,
                         @RequestParam @Nullable boolean onlyActive,
                         @PageableDefault(value = 15, sort = "date") Pageable pageable,
                         Model model) {
        model.addAttribute(PAGE, prescriptionService.filter(pDate, insuranceNumber, authDoctor, onlyActive, pageable));
        return PRESCRIPTIONS_URL;
    }

    @GetMapping("/today")
    public String today() {
        return "redirect:/prescriptions/filter?pDate=" + LocalDate.now() + "&insuranceNumber=&authDoctor=on";
    }

    @GetMapping
    public String prescriptions() {
        return "redirect:/prescriptions/filter?pDate=&insuranceNumber=";
    }

    @GetMapping("/new/{treatmentId}")
    @Secured("ROLE_DOCTOR")
    public String create(@PathVariable int treatmentId, Model model) {
        model.addAttribute(TREATMENT, treatmentService.getById(treatmentId));
        return CREATE_OR_UPDATE_PRESCRIPTION_URL;
    }

    @PostMapping("/new")
    @Secured("ROLE_DOCTOR")
    public String createPrescription(@Valid PrescriptionDto prescriptionDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return addAttributes(prescriptionDto, bindingResult, model);
        }
        var savedPrescription = prescriptionService.save(prescriptionDto);
        return REDIRECT_PRESCRIPTIONS_URL + savedPrescription.getId();
    }

    @GetMapping("/edit/{treatmentId}/{prescriptionId}")
    @Secured("ROLE_DOCTOR")
    public String update(@PathVariable int treatmentId, @PathVariable int prescriptionId, Model model) {
        model.addAttribute(TREATMENT, treatmentService.getById(treatmentId));
        model.addAttribute(PRESCRIPTION, prescriptionService.getById(prescriptionId));
        return CREATE_OR_UPDATE_PRESCRIPTION_URL;
    }

    @PostMapping("/edit")
    @Secured("ROLE_DOCTOR")
    public String updatePrescription(@Valid PrescriptionDto prescriptionDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return addAttributes(prescriptionDto, bindingResult, model);
        }
        var savedPrescription = prescriptionService.update(prescriptionDto);
        return REDIRECT_PRESCRIPTIONS_URL + savedPrescription.getId();
    }

    @GetMapping("/cancel/{id}")
    @Secured("ROLE_DOCTOR")
    public String cancel(@PathVariable int id, Model model) {
        model.addAttribute(PRESCRIPTION, prescriptionService.cancel(id));
        return REDIRECT_PRESCRIPTIONS_URL + id;
    }

    private String addAttributes(PrescriptionDto dto, BindingResult result, Model model) {
        model.addAllAttributes(ControllerUtil.getErrorsMap(result));
        model.addAttribute(PRESCRIPTION, dto);
        model.addAttribute(TREATMENT, treatmentService.getById(dto.getTreatmentId()));
        return CREATE_OR_UPDATE_PRESCRIPTION_URL;
    }
}
