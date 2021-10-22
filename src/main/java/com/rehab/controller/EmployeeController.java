package com.rehab.controller;

import com.rehab.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
@Secured("ROLE_ADMIN")
public class EmployeeController {

    private static final String EMPLOYEE = "employee";
    private static final String EMPLOYEE_URL = "/employees/employee";
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        model.addAttribute(EMPLOYEE, employeeService.getById(id));
        return EMPLOYEE_URL;
    }

    @GetMapping("/profile")
    @Secured({"ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_NURSE"})
    public String getAuth(Model model) {
        model.addAttribute(EMPLOYEE, employeeService.getAuth());
        return EMPLOYEE_URL;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("employees", employeeService.getAll());
        return "/employees/list";
    }
}
