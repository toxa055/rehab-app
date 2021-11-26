package com.rehab.util;

import com.rehab.exception.ApplicationException;
import com.rehab.model.Employee;
import com.rehab.security.EmployeeDetails;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * This class contains static utility method for getting current authenticated employee.
 * Instantiation of this class is prohibited.
 */
public class SecurityUtil {

    /**
     * Private empty constructor that indicates prohibition for creating instance of current class.
     */
    private SecurityUtil() {
    }

    /**
     * Returns current authenticated employee.
     *
     * @return current authenticated employee.
     * @throws ApplicationException if error occurs.
     */
    public static Employee getAuthEmployee() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ApplicationException("Cannot get auth employee.");
        }
        var principal = authentication.getPrincipal();
        if (principal instanceof EmployeeDetails) {
            return ((EmployeeDetails) principal).getEmployee();
        } else {
            throw new ApplicationException("Cannot get auth employee.");
        }
    }
}
