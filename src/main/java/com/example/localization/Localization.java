package com.example.localization;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.ResourceBundle;

@Service
public class Localization {

    private ResourceBundle messages;

    public void setLocale(String language) {
        if (language.equalsIgnoreCase("ru")) {
            messages = ResourceBundle.getBundle("messages_ru");
        } else {
            messages = ResourceBundle.getBundle("messages_en");
        }
    }

    public String getMessage(String key) {
        return messages.getString(key);
    }

}
