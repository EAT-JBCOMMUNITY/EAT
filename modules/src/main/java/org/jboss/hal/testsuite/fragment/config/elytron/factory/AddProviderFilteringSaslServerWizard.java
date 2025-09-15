package org.jboss.hal.testsuite.fragment.config.elytron.factory;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindowWithOptionalFields;

public class AddProviderFilteringSaslServerWizard extends WizardWindowWithOptionalFields {

    private static final String NAME = "name";
    private static final String SASL_SERVER_FACTORY = "sasl-server-factory";
    private static final String ENABLING = "enabling";

    public AddProviderFilteringSaslServerWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddProviderFilteringSaslServerWizard saslServerFactory(String value) {
        getEditor().text(SASL_SERVER_FACTORY, value);
        return this;
    }

    public AddProviderFilteringSaslServerWizard enabling(boolean value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().checkbox(ENABLING, value);
        return this;
    }

}
