package org.jboss.hal.testsuite.fragment.config.elytron.other.ssl;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddCredentialStoreWizard extends WizardWindow {

    private static final String NAME = "name";
    private static final String RELATIVE_TO = "relative-to";
    private static final String CREATE = "create";
    private static final String IMPLEMENTATION_PROPERTIES = "implementation-properties";
    private static final String LOCATION = "location";
    private static final String MODIFIABLE = "modifiable";
    private static final String OTHER_PROVIDERS = "other-providers";
    private static final String PROVIDER_NAME = "provider-name";
    private static final String PROVIDERS = "providers";
    private static final String TYPE = "type";
    private static final String CREDENTIAL_REFERENCE_STORE = "credential-reference-store";
    private static final String CREDENTIAL_REFERENCE_ALIAS = "credential-reference-alias";
    private static final String CREDENTIAL_REFERENCE_TYPE = "credential-reference-type";
    private static final String CREDENTIAL_REFERENCE_CLEAR_TEXT = "credential-reference-clear-text";


    public AddCredentialStoreWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddCredentialStoreWizard relativeTo(String value) {
        getEditor().text(RELATIVE_TO, value);
        return this;
    }

    public AddCredentialStoreWizard create(boolean value) {
        getEditor().checkbox(CREATE, value);
        return this;
    }

    public AddCredentialStoreWizard implementationProperties(String value) {
        getEditor().text(IMPLEMENTATION_PROPERTIES, value);
        return this;
    }

    public AddCredentialStoreWizard location(String value) {
        getEditor().text(LOCATION, value);
        return this;
    }

    public AddCredentialStoreWizard modifiable(boolean value) {
        getEditor().checkbox(MODIFIABLE, value);
        return this;
    }

    public AddCredentialStoreWizard otherProviders(String value) {
        getEditor().text(OTHER_PROVIDERS, value);
        return this;
    }

    public AddCredentialStoreWizard providerName(String value) {
        getEditor().text(PROVIDER_NAME, value);
        return this;
    }

    public AddCredentialStoreWizard providers(String value) {
        getEditor().text(PROVIDERS, value);
        return this;
    }

    public AddCredentialStoreWizard type(String value) {
        getEditor().text(TYPE, value);
        return this;
    }

    public AddCredentialStoreWizard credentialReferenceStore(String value) {
        getEditor().text(CREDENTIAL_REFERENCE_STORE, value);
        return this;
    }

    public AddCredentialStoreWizard credentialReferenceAlias(String value) {
        getEditor().text(CREDENTIAL_REFERENCE_ALIAS, value);
        return this;
    }

    public AddCredentialStoreWizard credentialReferenceType(String value) {
        getEditor().text(CREDENTIAL_REFERENCE_TYPE, value);
        return this;
    }

    public AddCredentialStoreWizard credentialReferenceClearText(String value) {
        getEditor().text(CREDENTIAL_REFERENCE_CLEAR_TEXT, value);
        return this;
    }
}
