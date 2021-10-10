package com.rehab.controller;

import com.rehab.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/prescriptions")
public class PrescriptionController {

    private static final String PRESCRIPTION_LIST = "/prescriptions/list";
    private final PrescriptionService prescriptionService;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @GetMapping(value = "/getBy", params = "id")
    public String getById(@RequestParam int id, Model model) {
        model.addAttribute("p", prescriptionService.getById(id));
        return "prescriptions/prescription";
    }

    @GetMapping(value = "/getBy", params = "patientId")
    public String getAllByPatientId(@RequestParam int patientId, Model model) {
        model.addAttribute("prescriptions", prescriptionService.getAllByPatientId(patientId));
        return PRESCRIPTION_LIST;
    }

    @GetMapping(value = "/getBy", params = "doctorId")
    public String getAllByDoctorId(@RequestParam int doctorId, Model model) {
        model.addAttribute("prescriptions", prescriptionService.getAllByDoctorId(doctorId));
        return PRESCRIPTION_LIST;
    }

    @GetMapping
    public String prescriptions(Model model) {
        model.addAttribute("prescriptions", prescriptionService.getAll());
        return PRESCRIPTION_LIST;
    }
}
