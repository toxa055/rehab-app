package com.rehab.model.type;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    DOCTOR,
    NURSE;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
