package org.jboss.hal.testsuite.fragment.config.webservices;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class WebServicesConfigurationPropertyWizard extends WizardWindow {
    public WebServicesConfigurationPropertyWizard key(String value) {
        getEditor().text("name", value);
        return this;
    }

    public WebServicesConfigurationPropertyWizard value(String value) {
        getEditor().text("value", value);
        return this;
    }
}
