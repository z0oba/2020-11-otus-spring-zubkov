package ru.otus.homework.service;

import org.springframework.stereotype.Service;

@Service
public class LoginServiceImp implements LoginService {

    private String userName;
    private boolean isLogged = false;

    @Override
    public boolean isLogged() {
        return isLogged;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.isLogged = true;
        this.userName = userName;
    }
}
