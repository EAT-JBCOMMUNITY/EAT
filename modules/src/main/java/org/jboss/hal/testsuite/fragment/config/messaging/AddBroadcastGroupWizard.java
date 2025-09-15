package org.jboss.hal.testsuite.fragment.config.messaging;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddBroadcastGroupWizard extends WizardWindow {

    public AddBroadcastGroupWizard name(String name) {
        getEditor().text("name", name);
        return this;
    }

    public AddBroadcastGroupWizard socketBinding(String socketBinding) {
        getEditor().text("socket-binding", socketBinding);
        return this;
    }

    public AddBroadcastGroupWizard connectors(String... connectors) {
        getEditor().text("connectors", String.join("\n", connectors));
        return this;
    }

}
