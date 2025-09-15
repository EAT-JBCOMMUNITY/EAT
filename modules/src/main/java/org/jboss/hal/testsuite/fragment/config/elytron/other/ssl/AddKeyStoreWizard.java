package org.jboss.hal.testsuite.fragment.config.elytron.other.ssl;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindowWithOptionalFields;

public class AddKeyStoreWizard extends WizardWindowWithOptionalFields {
    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final String ALIAS_FILTER = "alias-filter";
    private static final String PATH = "path";
    private static final String RELATIVE_TO = "relative-to";
    private static final String REQUIRED = "required";
    private static final String PROVIDER_NAME = "provider-name";
    private static final String PROVIDERS = "providers";
    private static final String CREDENTIAL_REFERENCE_STORE = "credential-reference-store";
    private static final String CREDENTIAL_REFERENCE_ALIAS = "credential-reference-alias";
    private static final String CREDENTIAL_REFERENCE_TYPE = "credential-reference-type";
    private static final String CREDENTIAL_REFERENCE_CLEAR_TEXT = "credential-reference-clear-text";

    public AddKeyStoreWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddKeyStoreWizard type(String value) {
        getEditor().text(TYPE, value);
        return this;
    }

    public AddKeyStoreWizard aliasFilter(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(ALIAS_FILTER, value);
        return this;
    }

    public AddKeyStoreWizard path(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(PATH, value);
        return this;
    }

    public AddKeyStoreWizard relativeTo(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(RELATIVE_TO, value);
        return this;
    }

    public AddKeyStoreWizard required(boolean value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().checkbox(REQUIRED, value);
        return this;
    }

    public AddKeyStoreWizard providerName(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(PROVIDER_NAME, value);
        return this;
    }

    public AddKeyStoreWizard providers(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(PROVIDERS, value);
        return this;
    }

    public AddKeyStoreWizard credentialReferenceStore(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(CREDENTIAL_REFERENCE_STORE, value);
        return this;
    }

    public AddKeyStoreWizard credentialReferenceAlias(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(CREDENTIAL_REFERENCE_ALIAS, value);
        return this;
    }

    public AddKeyStoreWizard credentialReferenceType(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(CREDENTIAL_REFERENCE_TYPE, value);
        return this;
    }

    public AddKeyStoreWizard credentialReferenceClearText(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(CREDENTIAL_REFERENCE_CLEAR_TEXT, value);
        return this;
    }
}
