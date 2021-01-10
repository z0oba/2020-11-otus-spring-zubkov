package ru.otus.homework.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@Getter
@Setter
@ConfigurationProperties(prefix = "application")
public class AppProps {

    private static final String DEFAULT_FILE_SUFFIX = ".csv";

    private Locale locale;
    private String delimiter;
    private String file;
    private int errorLimit;

    public String getFile(){
        return locale != null ?
                file.replace(DEFAULT_FILE_SUFFIX, "_" + locale + DEFAULT_FILE_SUFFIX) :
                file;
    }
}
