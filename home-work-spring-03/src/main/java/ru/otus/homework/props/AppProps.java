package ru.otus.homework.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@Getter
@Setter
@ConfigurationProperties(prefix = "application")
public class AppProps {
    private Locale locale;
    private String delimiter;
    private String file;
    private int errorLimit;
}
