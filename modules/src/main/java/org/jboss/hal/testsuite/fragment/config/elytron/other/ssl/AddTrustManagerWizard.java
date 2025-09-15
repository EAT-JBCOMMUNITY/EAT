package org.jboss.hal.testsuite.fragment.config.elytron.other.ssl;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindowWithOptionalFields;

public class AddTrustManagerWizard extends WizardWindowWithOptionalFields {

    private static final String NAME = "name";
    private static final String KEY_STORE = "key-store";
    private static final String ALGORITHM = "algorithm";
    private static final String ALIAS_FILTER = "alias-filter";
    private static final String PROVIDER_NAME = "provider-name";
    private static final String PROVIDERS = "providers";

    public AddTrustManagerWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddTrustManagerWizard keyStore(String value) {
        getEditor().text(KEY_STORE, value);
        return this;
    }

    public AddTrustManagerWizard algorithm(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(ALGORITHM, value);
        return this;
    }

    public AddTrustManagerWizard aliasFilter(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(ALIAS_FILTER, value);
        return this;
    }

    public AddTrustManagerWizard providerName(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(PROVIDER_NAME, value);
        return this;
    }

    public AddTrustManagerWizard providers(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(PROVIDERS, value);
        return this;
    }
}
