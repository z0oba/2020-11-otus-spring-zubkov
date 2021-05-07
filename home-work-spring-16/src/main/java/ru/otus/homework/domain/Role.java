package ru.otus.homework.domain;

import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {
    ADMIN, USER, GUEST;

    @Override
    public String getAuthority() {
        return name();
    }
}
