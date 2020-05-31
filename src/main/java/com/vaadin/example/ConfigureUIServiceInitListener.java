package com.vaadin.example;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.spring.annotation.SpringComponent;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

@SpringComponent
public class ConfigureUIServiceInitListener
        implements VaadinServiceInitListener {
    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        serviceInitEvent.getSource().addUIInitListener(uiInitEvent -> {
            initLanguage(uiInitEvent.getUI());
        });
    }

    private void initLanguage(UI ui) {
        Optional<Cookie> localeCookie = Arrays
                .stream(VaadinService.getCurrentRequest().getCookies())
                .filter(cookie -> "locale".equals(cookie.getName()))
                .findFirst();

        Locale locale;

        if (localeCookie.isPresent()) {
            locale = Locale.forLanguageTag(localeCookie.get().getValue());
        } else {
            locale = VaadinService.getCurrentRequest().getLocale();
        }

        if("".equals(locale.getLanguage()))
            locale = Locale.UK;

        ui.setLocale(locale);
    }
}
