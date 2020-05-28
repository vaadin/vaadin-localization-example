package com.vaadin.example;

import java.io.Serializable;
import java.util.Locale;

import com.vaadin.flow.i18n.I18NProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GreetService implements Serializable {

    @Autowired
    private I18NProvider i18NProvider;

    public String greet(String name, Locale locale) {

        if (name == null || name.isEmpty()) {
            return i18NProvider.getTranslation("anonymousGreeting", locale,
                    null);
        } else {
            return i18NProvider.getTranslation("greeting", locale, name);
        }
    }

}
