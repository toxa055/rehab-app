package com.rehab.controller;

import com.rehab.dto.PrescriptionDto;
import com.rehab.service.PrescriptionService;
import com.rehab.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/prescriptions")
@Secured({"ROLE_ADMIN", "ROLE_DOCTOR"})
public class PrescriptionController {

    private static final String PRESCRIPTIONS = "prescriptions";
    private static final String PRESCRIPTION_LIST = "/prescriptions/list";
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
        return "prescriptions/prescription";
    }

    @GetMapping(value = "/getBy", params = "patientId")
    public String getAllByPatientId(@RequestParam int patientId, Model model) {
        model.addAttribute(PRESCRIPTIONS, prescriptionService.getAllByPatientId(patientId));
        return PRESCRIPTION_LIST;
    }

    @GetMapping(value = "/getBy", params = "doctorId")
    public String getAllByDoctorId(@RequestParam int doctorId, Model model) {
        model.addAttribute(PRESCRIPTIONS, prescriptionService.getAllByDoctorId(doctorId));
        return PRESCRIPTION_LIST;
    }

    @GetMapping
    public String prescriptions(Model model) {
        model.addAttribute(PRESCRIPTIONS, prescriptionService.getAll());
        return PRESCRIPTION_LIST;
    }

    @GetMapping("/new/{treatmentId}")
    public String create(@PathVariable int treatmentId, Model model) {
        model.addAttribute("treatment", treatmentService.getById(treatmentId));
        return "prescriptions/new";
    }

    @PostMapping("/new")
    public String createPrescription(PrescriptionDto prescriptionDto) {
        prescriptionService.save(prescriptionDto);
        return "redirect:..";
    }

    @GetMapping("/cancel/{id}")
    public String cancel(@PathVariable int id, Model model) {
        model.addAttribute("p", prescriptionService.cancel(id));
        return "redirect:../" + id;
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable int id, @RequestParam int treatmentId, Model model) {
        prescriptionService.cancel(id);
        return "redirect:../new/" + treatmentId;
    }
}
