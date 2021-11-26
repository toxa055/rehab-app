package com.rehab.security;

import com.rehab.model.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;

/**
 * Class that provides main information about Employee as user of the application.
 *
 * @see com.rehab.service.EmployeeDetailsService
 */
public class EmployeeDetails implements UserDetails {

    /**
     * Identifier that is used to serialize/deserialize object.
     * It's mandatory attribute if class implements UserDetails.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Employee that is a user of the application.
     *
     * @see Employee
     */
    private Employee employee;

    /**
     * Empty constructor.
     */
    public EmployeeDetails() {
    }

    /**
     * Constructs current instance and initializes following fields.
     *
     * @param employee description of employee is in field declaration.
     */
    public EmployeeDetails(Employee employee) {
        this.employee = employee;
    }

    /**
     * Returns current employee.
     *
     * @return current employee.
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Returns granted authorities for current employee.
     *
     * @return collection of employee roles.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return employee.getRoles();
    }

    /**
     * Returns password of current employee.
     *
     * @return employee password.
     */
    @Override
    public String getPassword() {
        return employee.getPassword();
    }

    /**
     * Returns username of current employee.
     *
     * @return employee email.
     */
    @Override
    public String getUsername() {
        return employee.getEmail();
    }

    /**
     * Whether user account is expired.
     *
     * @return true.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Whether user account is not locked.
     *
     * @return true.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Whether user's credentials (password) is not expired.
     *
     * @return true.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Whether user account is enabled.
     *
     * @return true.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
