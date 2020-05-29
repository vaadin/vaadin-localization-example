package com.vaadin.example;

import com.vaadin.flow.i18n.I18NProvider;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * Simple implementation of {@link I18NProvider}.
 */
@Component
public class SimpleI18NProvider implements I18NProvider {
    private static final List<Locale> PROVIDED_LOCALES = Collections
            .unmodifiableList(Arrays.asList(Locales.ENGLISH, Locales.FRENCH,
                    Locales.FINNISH));
    private static Map<Locale, Map<String, String>> localeMap = initMap();
    private static final String PROPERTIES_FILE = "src/main/resources/labelsbundle_{0}.properties";

    private static Map<Locale, Map<String, String>> initMap() {
        Map<Locale, Map<String, String>> localeMap = new HashMap<>();

        for (Locale locale : PROVIDED_LOCALES) {
            String fileName = MessageFormat.format(PROPERTIES_FILE,
                    locale.getLanguage().toLowerCase());
            localeMap.put(locale, new HashMap<>());
            try (FileInputStream fileInputStream = new FileInputStream(
                    fileName)) {
                InputStreamReader inputStreamReader = new InputStreamReader(
                        fileInputStream, "UTF-8");
                Properties properties = new Properties();
                properties.load(inputStreamReader);
                for (String key : properties.stringPropertyNames()) {
                    String value = properties.getProperty(key);
                    localeMap.get(locale).put(key, value);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
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
