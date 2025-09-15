package org.jboss.hal.testsuite.fragment.config.elytron.factory;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindowWithOptionalFields;

public class AddConfigurableSaslServerWizard extends WizardWindowWithOptionalFields {
    private static final String NAME = "name";
    private static final String SASL_SERVER_FACTORY = "sasl-server-factory";
    private static final String PROPERTIES = "properties";
    private static final String PROTOCOL = "protocol";
    private static final String SERVER_NAME = "server-name";

    public AddConfigurableSaslServerWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddConfigurableSaslServerWizard saslServerFactory(String value) {
        getEditor().text(SASL_SERVER_FACTORY, value);
        return this;
    }

    public AddConfigurableSaslServerWizard properties(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(PROPERTIES, value);
        return this;
    }

    public AddConfigurableSaslServerWizard protocol(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(PROTOCOL, value);
        return this;
    }

    public AddConfigurableSaslServerWizard serverName(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(SERVER_NAME, value);
        return this;
    }
}
