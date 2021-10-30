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

    private static final String NEW_PRESCRIPTION_URL = "/prescriptions/new";
    private static final String REDIRECT = "redirect:../";
    private final PrescriptionService prescriptionService;
    private final TreatmentService treatmentService;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService, TreatmentService treatmentService) {
        this.prescriptionService = prescriptionService;
        this.treatmentService = treatmentService;
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        model.addAttribute("p", prescriptionService.getById(id));
        model.addAttribute("authDoctorId", SecurityUtil.getAuthEmployee().getId());
        return "prescriptions/prescription";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam @Nullable
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate pDate,
                         @RequestParam @Nullable Integer insuranceNumber,
                         @RequestParam @Nullable boolean authDoctor,
                         @RequestParam @Nullable boolean onlyActive,
                         Model model, @PageableDefault(15) Pageable pageable) {
        model.addAttribute("page", prescriptionService.filter(pDate, insuranceNumber, authDoctor,
                onlyActive, pageable));
        return "/prescriptions/list";
    }

    @GetMapping("/today")
    public String today(Model model) {
        return "redirect:/prescriptions/filter?pDate=" + LocalDate.now() + "&insuranceNumber=";
    }

    @GetMapping
    public String prescriptions(Model model) {
        return "redirect:/prescriptions/filter?pDate=&insuranceNumber=";
    }

    @GetMapping("/new/{treatmentId}")
    public String create(@PathVariable int treatmentId, Model model) {
        model.addAttribute("treatment", treatmentService.getById(treatmentId));
        return NEW_PRESCRIPTION_URL;
    }

    @PostMapping("/new")
    public String createPrescription(@Valid PrescriptionDto prescriptionDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAllAttributes(ControllerUtil.getErrorsMap(bindingResult));
            model.addAttribute("p", prescriptionDto);
            model.addAttribute("treatment", treatmentService.getById(prescriptionDto.getTreatmentId()));
            return NEW_PRESCRIPTION_URL;
        }
        var savedPrescription = prescriptionService.save(prescriptionDto);
        return REDIRECT + savedPrescription.getId();
    }

    @GetMapping("/cancel/{id}")
    public String cancel(@PathVariable int id, Model model) {
        model.addAttribute("p", prescriptionService.cancel(id));
        return REDIRECT + id;
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable int id, @RequestParam int treatmentId, Model model) {
        prescriptionService.cancel(id);
        return "redirect:../new/" + treatmentId;
    }
}
