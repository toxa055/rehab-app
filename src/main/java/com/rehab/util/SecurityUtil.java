package com.rehab.util;

import com.rehab.model.Employee;
import com.rehab.security.EmployeeDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static Employee getAuthEmployee() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (principal instanceof EmployeeDetails) ? ((EmployeeDetails) principal).getEmployee() : null;
    }
}
