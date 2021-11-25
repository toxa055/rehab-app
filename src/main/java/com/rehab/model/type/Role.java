package com.rehab.model.type;

import org.springframework.security.core.GrantedAuthority;

/**
 * Enumeration which stores user roles. Every role has its own granted authorities.
 * User with any role can do following: see lists of patients, events, cures;
 * see their own profile; change password; create new cures.
 */
public enum Role implements GrantedAuthority {

    /**
     * This role allows user to see all the information, create and edit patients and employees.
     */
    ADMIN,

    /**
     * This role allows user to see all the information (except employees),
     * create patients, make actions with patients, treatments, prescriptions.
     */
    DOCTOR,

    /**
     * This role allows user to make actions with events.
     */
    NURSE;

    /**
     * Method to add prefix 'ROLE_' to any role that is mandatory to have granted authorities.
     *
     * @return role name with prefix 'ROLE_'.
     */
    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
