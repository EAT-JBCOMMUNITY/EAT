package org.jboss.hal.testsuite.fragment.config.elytron.other.ssl;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddClientSSLContextWizard extends WizardWindow {

    private static final String NAME = "name";
    private static final String CIPHER_SUITE_FILTER = "cipher-suite-filter";
    private static final String KEY_MANAGER = "key-manager";
    private static final String PROTOCOLS = "protocols";
    private static final String PROVIDER_NAME = "provider-name";
    private static final String PROVIDERS = "providers";
    private static final String TRUST_MANAGER = "trust-manager";

    public AddClientSSLContextWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddClientSSLContextWizard cipherSuiteFilter(String value) {
        getEditor().text(CIPHER_SUITE_FILTER, value);
        return this;
    }

    public AddClientSSLContextWizard keyManager(String value) {
        getEditor().text(KEY_MANAGER, value);
        return this;
    }

    public AddClientSSLContextWizard protocols(String value) {
        getEditor().text(PROTOCOLS, value);
        return this;
    }

    public AddClientSSLContextWizard providerName(String value) {
        getEditor().text(PROVIDER_NAME, value);
        return this;
    }

    public AddClientSSLContextWizard providers(String value) {
        getEditor().text(PROVIDERS, value);
        return this;
    }

    public AddClientSSLContextWizard trustManager(String value) {
        getEditor().text(TRUST_MANAGER, value);
        return this;
    }
}
