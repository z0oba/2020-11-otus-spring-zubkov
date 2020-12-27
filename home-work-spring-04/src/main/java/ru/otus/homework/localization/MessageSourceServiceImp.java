package ru.otus.homework.localization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.homework.props.AppProps;

@Service
public class MessageSourceServiceImp implements MessageSourceService {

    private final MessageSource messageSource;
    private final AppProps appProps;

    @Autowired
    public MessageSourceServiceImp(MessageSource messageSource, AppProps appProps) {
        this.messageSource = messageSource;
        this.appProps = appProps;
    }

    @Override
    public String getMessage(String code, String defaultMessage, Object... args) {
        return messageSource.getMessage(code, args, defaultMessage, appProps.getLocale());
    }
}
