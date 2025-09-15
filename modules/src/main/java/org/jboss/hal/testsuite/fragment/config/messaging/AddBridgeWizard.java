package org.jboss.hal.testsuite.fragment.config.messaging;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddBridgeWizard extends WizardWindow {

    public AddBridgeWizard name(String name) {
        getEditor().text("name", name);
        return this;
    }

    public AddBridgeWizard queueName(String queueName) {
        getEditor().text("queueName", queueName);
        return this;
    }

    public AddBridgeWizard discoveryGroup(String discoveryGroup) {
        getEditor().text("discoveryGroup", discoveryGroup);
        return this;
    }

    public AddBridgeWizard forwardingAddress(String forwardAddress) {
        getEditor().text("forwardingAddress", forwardAddress);
        return this;
    }

    public AddBridgeWizard staticConnectors(String... staticConnectors) {
        getEditor().text("staticConnectors", String.join("\n", staticConnectors));
        return this;
    }

}
