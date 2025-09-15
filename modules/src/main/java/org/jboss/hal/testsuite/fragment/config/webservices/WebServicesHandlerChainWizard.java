package org.jboss.hal.testsuite.fragment.config.webservices;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class WebServicesHandlerChainWizard extends WizardWindow {
    public WebServicesHandlerChainWizard name(String value) {
        getEditor().text("name", value);
        return this;
    }

    public WebServicesHandlerChainWizard protocolBindings(String value) {
        getEditor().text("protocol-bindings", value);
        return this;
    }
}
