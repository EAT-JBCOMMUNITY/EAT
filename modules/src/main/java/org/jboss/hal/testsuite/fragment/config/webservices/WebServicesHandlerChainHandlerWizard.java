package org.jboss.hal.testsuite.fragment.config.webservices;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class WebServicesHandlerChainHandlerWizard extends WizardWindow {
    public WebServicesHandlerChainHandlerWizard name(String value) {
        getEditor().text("name", value);
        return this;
    }

    public WebServicesHandlerChainHandlerWizard className(String value) {
        getEditor().text("class", value);
        return this;
    }
}
