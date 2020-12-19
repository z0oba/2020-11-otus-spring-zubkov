package ru.otus.homework.localization;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class LocalizationConfig {

    @Value("${messageSource.basename}")
    private String basename;
    @Value("${messageSource.defaultEncoding}")
    private String defaultEncoding;

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename(basename);
        ms.setDefaultEncoding(defaultEncoding);
        return ms;
    }
}