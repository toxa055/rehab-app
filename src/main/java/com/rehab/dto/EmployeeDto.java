package com.rehab.dto;

import java.util.Objects;

/**
 * Data Transfer Object for {@link com.rehab.model.Employee}, i.e. it's a view representation of Employee model.
 * It's used for transferring data between services and controllers.
 */
public class EmployeeDto {

    /**
     * Employee id.
     */
    private Integer id;

    /**
     * Employee name, second name.
     */
    private String name;

    /**
     * Employee job position.
     */
    private String position;

    /**
     * Employee email address.
     */
    private String email;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare.
     * @return true if this object is the same as the o argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDto that = (EmployeeDto) o;
        return id.equals(that.id)
                && name.equals(that.name)
                && position.equals(that.position)
                && email.equals(that.email);
    }

    /**
     * Returns a hash code value for current object.
     *
     * @return a hash code value for the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, position, email);
    }
}
