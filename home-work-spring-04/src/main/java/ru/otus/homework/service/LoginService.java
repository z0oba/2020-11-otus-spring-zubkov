package ru.otus.homework.service;

public interface LoginService {
    boolean isLogged();

    String getUserName();

    void setUserName(String userName);
}
