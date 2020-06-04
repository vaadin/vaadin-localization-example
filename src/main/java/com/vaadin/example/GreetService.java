package com.vaadin.example;

import java.io.Serializable;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaadin.flow.i18n.I18NProvider;

/**
 * Simple service demonstrating that we can use the translations in non-UI
 * classes as well.
 */
@Service
public class GreetService implements Serializable {

	@Autowired
	private I18NProvider i18NProvider;

	public String greet(String name, Locale locale) {

		if (name == null || name.isEmpty()) {
			return i18NProvider.getTranslation("service.anonymousGreeting", locale);
		} else {
			return i18NProvider.getTranslation("service.greeting", locale, name);
		}
	}
}
