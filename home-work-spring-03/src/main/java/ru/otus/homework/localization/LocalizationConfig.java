package ru.otus.homework.localization;

import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalizationConfig extends MessageSourceAutoConfiguration { //create messageSourceBean by MessageSourceAutoConfiguration
}