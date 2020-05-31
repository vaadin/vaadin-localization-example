package com.vaadin.example;

import com.vaadin.flow.i18n.I18NProvider;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Simple implementation of {@link I18NProvider}.
 */
@Component
public class SimpleI18NProvider implements I18NProvider {

    public static final java.util.Locale FINNISH = new java.util.Locale("fi",
            "FI");
    public static final java.util.Locale ENGLISH = Locale.UK;
    public static final java.util.Locale FRENCH = Locale.FRANCE;

    private static final List<Locale> PROVIDED_LOCALES = Collections
            .unmodifiableList(Arrays.asList(ENGLISH, FRENCH,
                    FINNISH));
    private static Map<Locale, Map<String, String>> localeMap = initMap();
    private static final String PROPERTIES_FILE = "src/main/resources/labelsbundle_{0}.properties";

    private static Map<Locale, Map<String, String>> initMap() {
        Map<Locale, Map<String, String>> localeMap = new HashMap<>();

        for (Locale locale : PROVIDED_LOCALES) {
            String fileName = MessageFormat.format(PROPERTIES_FILE,
                    locale.getLanguage().toLowerCase());
            localeMap.put(locale, new HashMap<>());
            ResourceBundle resourceBundle = ResourceBundle
                    .getBundle("labelsbundle", locale);
            for (String key : resourceBundle.keySet()) {
                String value = resourceBundle.getString(key);
                value = new String(value.getBytes(StandardCharsets.ISO_8859_1),
                        StandardCharsets.UTF_8);
                localeMap.get(locale).put(key, value);
            }
        }
        return localeMap;
    }

    @Override
    public List<Locale> getProvidedLocales() {
        return PROVIDED_LOCALES;
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {

        if (params != null && params.length > 0)
            return MessageFormat.format(localeMap.get(locale).get(key), params);

        return localeMap.get(locale).get(key);
    }
}
