package com.vaadin.example;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

import jakarta.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.spring.annotation.SpringComponent;

/**
 * Class allowing us to customize the startup process whenever the Application
 * starts and/or whenever a users navigates to the app.
 */
@SpringComponent
public class ServiceInitListener implements VaadinServiceInitListener {

    @Autowired
    private I18NProvider i18nProvider;

    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        serviceInitEvent.getSource().addUIInitListener(uiInitEvent -> {

            // Whenever a new user arrives, determine locale
            initLanguage(uiInitEvent.getUI());
        });
    }

    private void initLanguage(UI ui) {

        Optional<Cookie> localeCookie = Optional.empty();

        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
        if (cookies != null) {
            localeCookie = Arrays.stream(cookies).filter(cookie -> "locale".equals(cookie.getName())).findFirst();
        }

        Locale locale;

        if (localeCookie.isPresent() && !"".equals(localeCookie.get().getValue())) {
            // Cookie found, use that
            locale = Locale.forLanguageTag(localeCookie.get().getValue());
        } else {
            // Try to use Vaadin's browser locale detection
            locale = VaadinService.getCurrentRequest().getLocale();
        }

        // If the detection fails, default to the first language we support.
        if (locale.getLanguage().equals("")) {
            locale = i18nProvider.getProvidedLocales().get(0);
        }

        ui.setLocale(locale);
    }
}
