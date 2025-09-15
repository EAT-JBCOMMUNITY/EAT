package org.jboss.hal.testsuite.fragment.config.remoting;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class HttpConnectorWizard extends WizardWindow {

    public HttpConnectorWizard name(String name) {
        getEditor().text("name", name);
        return this;
    }

    public HttpConnectorWizard connectorRef(String connectorRef) {
        getEditor().text("connector-ref", connectorRef);
        return this;
    }
}