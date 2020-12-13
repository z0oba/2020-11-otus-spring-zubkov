package ru.otus.homework.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageSourceImp implements MessageSource {

    private final ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
    private final Locale locale;

    public MessageSourceImp(@Value("${locale}")Locale locale){
        this.locale = locale;
        ms.setBasename("classpath:/bundle");
        ms.setDefaultEncoding("UTF-8");
    }

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return ms.getMessage(code, args, defaultMessage, locale);
    }

    @Override
    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        return ms.getMessage(code,args,locale);
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        return ms.getMessage(resolvable, locale);
    }

    public String getMessage(String code, String defaultMessage) {
        return ms.getMessage(code, null, defaultMessage, locale);
    }

    public String getFile(String code, Locale locale){
        return ms.getMessage(code, null, locale);
    }

    public String getFile(String code){
        return ms.getMessage(code, null, locale);
    }

    public String getFile(String code, String defaultMessage, Locale locale){
        return ms.getMessage(code, null, defaultMessage, locale);
    }

    public String getFile(String code, String defaultMessage){
        return ms.getMessage(code, null, defaultMessage, locale);
    }
}
