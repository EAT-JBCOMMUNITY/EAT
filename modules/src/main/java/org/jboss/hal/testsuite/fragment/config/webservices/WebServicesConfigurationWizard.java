package org.jboss.hal.testsuite.fragment.config.webservices;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;


public class WebServicesConfigurationWizard extends WizardWindow {
    public WebServicesConfigurationWizard name(String value) {
        getEditor().text("name", value);
        return this;
    }
}
