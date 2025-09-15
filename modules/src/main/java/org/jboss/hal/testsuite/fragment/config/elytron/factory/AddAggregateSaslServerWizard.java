package org.jboss.hal.testsuite.fragment.config.elytron.factory;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddAggregateSaslServerWizard extends WizardWindow {
    private static final String NAME = "name";
    private static final String SASL_SERVER_MECHANISM_FACTORIES = "sasl-server-factories";

    public AddAggregateSaslServerWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddAggregateSaslServerWizard saslServerMechanismFactories(String value) {
        getEditor().text(SASL_SERVER_MECHANISM_FACTORIES, value);
        return this;
    }
}
