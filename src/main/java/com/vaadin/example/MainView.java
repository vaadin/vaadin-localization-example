package com.vaadin.example;

import java.util.Locale;

import com.vaadin.flow.i18n.I18NProvider;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.Route;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and use @Route
 * annotation to announce it in a URL as a Spring managed bean.
 * <p>
 * A new instance of this class is created for every new user and every browser
 * tab/window.
 * <p>
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */
@Route()
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout implements LocaleChangeObserver {

    private TextField textField;
    private Button button;
    private Select<Locale> selectLanguage;

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service
     *            The message service. Automatically injected Spring managed
     *            bean.
     */
    public MainView(@Autowired GreetService service,
            @Autowired I18NProvider i18NProvider) {

        // select component for selecting a language
        selectLanguage = new Select<>();
        selectLanguage.setLabel("selectLanguage");
        selectLanguage.setItems(i18NProvider.getProvidedLocales());
        selectLanguage.setItemLabelGenerator(Locale::getDisplayCountry);
        selectLanguage.addValueChangeListener(
                event -> getUI().get().setLocale(selectLanguage.getValue()));

        // Use TextField for standard text input
        textField = new TextField(getTranslation("yourName"));

        // Button click listeners can be defined as lambda expressions
        button = new Button(getTranslation("helloButton"),
                e -> Notification.show(service.greet(textField.getValue(),
                        getUI().get().getLocale())));

        // Theme variants give you predefined extra styles for components.
        // Example: Primary button is more prominent look.
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        button.addClickShortcut(Key.ENTER);

        // Use custom CSS classes to apply styling. This is defined in
        // shared-styles.css.
        addClassName("centered-content");

        add(selectLanguage, textField, button);
    }

    @Override
    public void localeChange(LocaleChangeEvent localeChangeEvent) {

        // Set key for components to get the translation of each component
        // label from localeMap in SimpleI18NProvider class.
        button.setText(getTranslation("helloButton"));
        textField.setLabel(getTranslation("yourName"));
        selectLanguage.setLabel(getTranslation("selectLanguage"));
    }
}
