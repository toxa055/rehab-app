package com.rehab.controller;

import com.rehab.dto.UserDto;
import com.rehab.model.type.Role;
import com.rehab.service.EmployeeService;
import com.rehab.util.ControllerUtil;
import com.rehab.util.SecurityUtil;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/employees")
@Secured("ROLE_ADMIN")
public class EmployeeController {

    private static final String EDIT_EMPLOYEE_URL = "/employees/edit";
    private static final String NEW_EMPLOYEE_URL = "/employees/new";
    private static final String EMPLOYEE_URL = "/employees/employee";
    private static final String EMPLOYEE = "employee";
    private static final String DIFF_PASSWORDS = "Passwords are not equal";
    private static final String PASSWORD_ERROR = "passwordError";
    private final EmployeeService employeeService;
    private final Logger logger;

    @Autowired
    public EmployeeController(EmployeeService employeeService, Logger logger) {
        this.employeeService = employeeService;
        this.logger = logger;
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        logger.info("Get employee by id {}.", id);
        model.addAttribute(EMPLOYEE, employeeService.getById(id));
        model.addAttribute("authId", employeeService.getAuth().getId());
        return EMPLOYEE_URL;
    }

    @GetMapping("/profile")
    @Secured({"ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_NURSE"})
    public String getAuth(Model model) {
        var authEmployee = employeeService.getAuth();
        var id = authEmployee.getId();
        logger.info("Get authenticated employee with id {}.", id);
        model.addAttribute(EMPLOYEE, authEmployee);
        model.addAttribute("authId", id);
        return EMPLOYEE_URL;
    }

    @GetMapping("/{id}/edit")
    public String editById(@PathVariable int id, Model model) {
        model.addAttribute(EMPLOYEE, employeeService.getById(id));
        return EDIT_EMPLOYEE_URL;
    }

    @GetMapping("/profile/edit")
    @Secured({"ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_NURSE"})
    public String editAuth(Model model) {
        model.addAttribute(EMPLOYEE, employeeService.getAuth());
        return EDIT_EMPLOYEE_URL;
    }

    @PostMapping("/edit")
    @Secured({"ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_NURSE"})
    public String edit(@Valid UserDto userDto, BindingResult bindingResult, Model model) {
        model.addAttribute(EMPLOYEE, userDto);
        if (bindingResult.hasErrors()) {
            logger.warn("Binding result for employee has errors: {}", bindingResult.getAllErrors());
            var errorsMap = ControllerUtil.getErrorsMap(bindingResult);
            if (errorsMap.containsKey(PASSWORD_ERROR)) {
                model.addAllAttributes(errorsMap);
                return EDIT_EMPLOYEE_URL;
            }
        }
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            logger.warn("Password [{}] and password confirmation [{}] are not equal.",
                    userDto.getPassword(), userDto.getConfirmPassword());
            model.addAttribute(PASSWORD_ERROR, DIFF_PASSWORDS);
            return EDIT_EMPLOYEE_URL;
        }
        logger.info("Change password for employee with id {}.", userDto.getId());
        employeeService.changePassword(userDto);
        return SecurityUtil.getAuthEmployee().getId().equals(userDto.getId()) ? "redirect:profile" : "redirect:";
    }

    @GetMapping("/new")
    public String create() {
        return NEW_EMPLOYEE_URL;
    }

    @PostMapping("/new")
    public String create(@Valid UserDto userDto, BindingResult bindingResult, Model model) {
        model.addAttribute(EMPLOYEE, userDto);
        var password = userDto.getPassword();
        var passwordConfirmation = userDto.getConfirmPassword();
        var isDifferentPasswords = (password != null) && (!password.equals(passwordConfirmation));
        if (isDifferentPasswords) {
            logger.warn("Password [{}] and password confirmation [{}] are not equal.",
                    password, passwordConfirmation);
            model.addAttribute(PASSWORD_ERROR, DIFF_PASSWORDS);
            model.addAttribute("confirmPasswordError", DIFF_PASSWORDS);
        }
        if (bindingResult.hasErrors()) {
            logger.warn("Binding result for employee has errors: {}", bindingResult.getAllErrors());
            var errorsMap = ControllerUtil.getErrorsMap(bindingResult);
            var roles = userDto.getRoles();
            if ((roles != null) && roles.contains(Role.DOCTOR) && roles.contains(Role.NURSE)) {
                errorsMap.put("rolesError", "Cannot choose roles DOCTOR and NURSE together");
            }
            model.mergeAttributes(errorsMap);
        }
        if (isDifferentPasswords || bindingResult.hasErrors()) {
            return NEW_EMPLOYEE_URL;
        }
        logger.info("Create new employee.");
        employeeService.save(userDto);
        return "redirect:";
    }

    @GetMapping
    public String getAll(@PageableDefault Pageable pageable, Model model) {
        logger.info("Get all employees.");
        model.addAttribute("page", employeeService.getAll(pageable));
        return "/employees/list";
    }
}
