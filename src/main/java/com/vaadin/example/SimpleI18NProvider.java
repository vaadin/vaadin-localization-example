package com.vaadin.example;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.vaadin.flow.i18n.I18NProvider;

/**
 * Simple implementation of {@link I18NProvider}.
 */
@Component
public class SimpleI18NProvider implements I18NProvider {

    private static Map<Locale, Map<String, String>> localeMap = initMap();

    private static Map<Locale, Map<String, String>> initMap() {
        Map<Locale, Map<String, String>> localeMap = new HashMap<>();

        localeMap.put(Locales.FINNISH, new HashMap<>());
        localeMap.get(Locales.FINNISH).put("helloButton", "Sano Hei");
        localeMap.get(Locales.FINNISH).put("yourName", "Sinun nimesi");
        localeMap.get(Locales.FINNISH).put("selectLanguage", "Valitse kieli");
        localeMap.get(Locales.FINNISH).put("greeting", "Hei {0}!");
        localeMap.get(Locales.FINNISH).put("anonymousGreeting",
                "Hei tuntematon!");

        localeMap.put(Locales.ENGLISH, new HashMap<>());
        localeMap.get(Locales.ENGLISH).put("helloButton", "Say hello");
        localeMap.get(Locales.ENGLISH).put("yourName", "Your name");
        localeMap.get(Locales.ENGLISH).put("selectLanguage",
                "Select a language");
        localeMap.get(Locales.ENGLISH).put("greeting", "Hello {0}!");
        localeMap.get(Locales.ENGLISH).put("anonymousGreeting",
                "Hello anonymous!");

        localeMap.put(Locales.FRENCH, new HashMap<>());
        localeMap.get(Locales.FRENCH).put("helloButton", "Dire bonjoure");
        localeMap.get(Locales.FRENCH).put("yourName", "Votre nom");
        localeMap.get(Locales.FRENCH).put("selectLanguage",
                "Sélectionnez une langue");
        localeMap.get(Locales.FRENCH).put("greeting", "Bonjoure {0}!");
        localeMap.get(Locales.FRENCH).put("anonymousGreeting",
                "Bonjour anonyme!");

        return localeMap;
    }

    @Override
    public List<Locale> getProvidedLocales() {
        return new ArrayList<>(localeMap.keySet());
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {

        if (params != null && params.length > 0)
            return MessageFormat.format(localeMap.get(locale).get(key), params);

        return localeMap.get(locale).get(key);
    }
}
