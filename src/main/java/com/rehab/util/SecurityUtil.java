package com.rehab.util;

import com.rehab.exception.ApplicationException;
import com.rehab.model.Employee;
import com.rehab.security.EmployeeDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static Employee getAuthEmployee() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof EmployeeDetails) {
            return ((EmployeeDetails) principal).getEmployee();
        } else {
            throw new ApplicationException("Cannot get auth employee.");
        }
    }
}
