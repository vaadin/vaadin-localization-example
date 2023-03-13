package com.vaadin.example;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.vaadin.flow.i18n.I18NProvider;

/**
 * Simple implementation of {@link I18NProvider}.
 * <p>
 * Actual translations can be found in the labelsbundle_{lang_code}.properties
 * files.
 * <p>
 * Singleton scope.
 */
@Component
public class SimpleI18NProvider implements I18NProvider {

	/*
	 * Use no-country versions, so that e.g. both en_US and en_GB work.
	 */
	public static final java.util.Locale FINNISH = new Locale("fi");
	public static final java.util.Locale ENGLISH = new Locale("en");
	public static final java.util.Locale FRENCH = new Locale("fr");

	private Map<String, ResourceBundle> localeMap;

	@PostConstruct
	private void initMap() {
		localeMap = new HashMap<>();

		// Read translations file for each locale
		for (final Locale locale : getProvidedLocales()) {

			final ResourceBundle resourceBundle = ResourceBundle.getBundle("labelsbundle", locale);
			localeMap.put(locale.getLanguage(), resourceBundle);
		}
	}

	@Override
	public List<Locale> getProvidedLocales() {
		return Collections.unmodifiableList(Arrays.asList(ENGLISH, FRENCH, FINNISH));
	}

	@Override
	public String getTranslation(String key, Locale locale, Object... params) {

		String rawstring = null;
		try {
			rawstring = localeMap.get(locale.getLanguage()).getString(key);

			return MessageFormat.format(rawstring, params);

		} catch (final MissingResourceException e) {
			// Translation not found, return error message instead of null as per API
			System.out.println(String.format("No translation found for key {%s}", key));
			return String.format("!{%s}", key);
		} catch (final IllegalArgumentException e) {
			e.printStackTrace(); // for devs to find where this happened
			// Incorrect parameters
			return rawstring;
		}

	}
}
