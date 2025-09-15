package org.jboss.hal.testsuite.fragment.config.elytron.factory;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindowWithOptionalFields;

public class AddConfigurableHttpServerWizard extends WizardWindowWithOptionalFields {
    private static final String NAME = "name";
    private static final String HTTP_SERVER_MECHANISM_FACTORY = "http-server-mechanism-factory";
    private static final String PROPERTIES = "properties";

    public AddConfigurableHttpServerWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddConfigurableHttpServerWizard httpServerMechanismFactory(String value) {
        getEditor().text(HTTP_SERVER_MECHANISM_FACTORY, value);
        return this;
    }

    public AddConfigurableHttpServerWizard properties(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(PROPERTIES, value);
        return this;
    }
}
