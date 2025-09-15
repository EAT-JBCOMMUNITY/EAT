package org.jboss.hal.testsuite.fragment.config.elytron.factory;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindowWithOptionalFields;

public class AddProviderFilteringSaslFilterWizard extends WizardWindowWithOptionalFields {
    private static final String VERSION_COMPARISON = "version-comparison";
    private static final String MECHANISM_NAME = "mechanism-name";
    private static final String PROVIDER_VERSION = "provider-version";
    private static final String PROVIDER_NAME = "provider-name";

    public AddProviderFilteringSaslFilterWizard providerName(String value) {
        getEditor().text(PROVIDER_NAME, value);
        return this;
    }

    public AddProviderFilteringSaslFilterWizard mechanismName(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(MECHANISM_NAME, value);
        return this;
    }

    public AddProviderFilteringSaslFilterWizard providerVersion(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(PROVIDER_VERSION, value);
        return this;
    }

    public AddProviderFilteringSaslFilterWizard versionComparison(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().select(VERSION_COMPARISON, value);
        return this;
    }


}
