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

/**
 * Controller that executes http requests for Employee.
 * Any method returns name of jsp page to show it to user or redirects to another.
 * Almost any method adds to model execution result of employeeService (as attribute).
 */
@Controller
@RequestMapping("/employees")
@Secured("ROLE_ADMIN")
public class EmployeeController {

    /**
     * Name of jsp page that contains form for editing employee.
     */
    private static final String EDIT_EMPLOYEE_URL = "/employees/edit";

    /**
     * Name of jsp page that contains form for creating employee.
     */
    private static final String NEW_EMPLOYEE_URL = "/employees/new";

    /**
     * Name of jsp page that shows only one employee.
     */
    private static final String EMPLOYEE_URL = "/employees/employee";

    /**
     * Name of model attribute for Employee.
     */
    private static final String EMPLOYEE = "employee";

    /**
     * Value of model attribute for {@link #PASSWORD_ERROR}.
     */
    private static final String DIFF_PASSWORDS = "Passwords are not equal";

    /**
     * Name of model attribute for passwordError.
     */
    private static final String PASSWORD_ERROR = "passwordError";

    /**
     * EmployeeService bean.
     */
    private final EmployeeService employeeService;

    /**
     * Interface that logs specific messages.
     */
    private final Logger logger;

    /**
     * Constructs new instance and initializes following fields.
     *
     * @param employeeService description of employeeService is in field declaration.
     * @param logger          description of logger is in field declaration.
     */
    @Autowired
    public EmployeeController(EmployeeService employeeService, Logger logger) {
        this.employeeService = employeeService;
        this.logger = logger;
    }

    /**
     * Method executes GET request to receive employee by given id.
     *
     * @param id    employee id.
     * @param model holder for model attributes.
     * @return name of jsp page.
     */
    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        logger.info("Get employee by id {}.", id);
        model.addAttribute(EMPLOYEE, employeeService.getById(id));
        model.addAttribute("authId", employeeService.getAuth().getId());
        return EMPLOYEE_URL;
    }

    /**
     * Method executes GET request to receive authenticated employee.
     *
     * @param model holder for model attributes.
     * @return name of jsp page.
     */
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

    /**
     * Method executes GET request to open jsp page where employee (by given id) will be edited.
     *
     * @param id    employee id
     * @param model holder for model attributes.
     * @return name of jsp page.
     */
    @GetMapping("/{id}/edit")
    public String editById(@PathVariable int id, Model model) {
        model.addAttribute(EMPLOYEE, employeeService.getById(id));
        return EDIT_EMPLOYEE_URL;
    }

    /**
     * Method executes GET request to open jsp page where authenticated employee will be edited.
     *
     * @param model holder for model attributes.
     * @return name of jsp page.
     */
    @GetMapping("/profile/edit")
    @Secured({"ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_NURSE"})
    public String editAuth(Model model) {
        model.addAttribute(EMPLOYEE, employeeService.getAuth());
        return EDIT_EMPLOYEE_URL;
    }

    /**
     * Method executes POST request to change password for given employee.
     * Given userDto is validated before saving. Password and password confirmation must be equal.
     *
     * @param userDto       that password will be changed.
     * @param bindingResult holder for field errors.
     * @param model         holder for model attributes.
     * @return redirect to profile or list of employees.
     */
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

    /**
     * Method executes GET request to open jsp page where new employee will be created.
     *
     * @return name of jsp page.
     */
    @GetMapping("/new")
    public String create() {
        return NEW_EMPLOYEE_URL;
    }

    /**
     * Method executes POST request to create new employee.
     * Given userDto is validated before saving. Password and password confirmation must be equal.
     * Roles 'DOCTOR' and 'NURSE' are not allowed to be together.
     *
     * @param userDto       that will be created.
     * @param bindingResult holder for field errors.
     * @param model         holder for model attributes.
     * @return redirect to list of employees.
     */
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

    /**
     * Method executes GET request to receive list of all employees.
     *
     * @param pageable interface that provides pagination.
     * @param model    holder for model attributes.
     * @return name of jsp page.
     */
    @GetMapping
    public String getAll(@PageableDefault Pageable pageable, Model model) {
        logger.info("Get all employees.");
        model.addAttribute("page", employeeService.getAll(pageable));
        return "/employees/list";
    }
}
