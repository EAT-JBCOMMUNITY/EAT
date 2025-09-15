package org.jboss.hal.testsuite.fragment.config.elytron.other.ssl;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindowWithOptionalFields;

public class AddKeyManagerWizard extends WizardWindowWithOptionalFields {

    private static final String NAME = "name";
    private static final String KEY_STORE = "key-store";
    private static final String ALGORITHM = "algorithm";
    private static final String ALIAS_FILTER = "alias-filter";
    private static final String PROVIDER_NAME = "provider-name";
    private static final String PROVIDERS = "providers";
    private static final String CREDENTIAL_REFERENCE_STORE = "credential-reference-store";
    private static final String CREDENTIAL_REFERENCE_ALIAS = "credential-reference-alias";
    private static final String CREDENTIAL_REFERENCE_TYPE = "credential-reference-type";
    private static final String CREDENTIAL_REFERENCE_CLEAR_TEXT = "credential-reference-clear-text";

    public AddKeyManagerWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddKeyManagerWizard keyStore(String value) {
        getEditor().text(KEY_STORE, value);
        return this;
    }

    public AddKeyManagerWizard algorithm(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(ALGORITHM, value);
        return this;
    }

    public AddKeyManagerWizard aliasFilter(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(ALIAS_FILTER, value);
        return this;
    }

    public AddKeyManagerWizard providerName(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(PROVIDER_NAME, value);
        return this;
    }

    public AddKeyManagerWizard providers(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(PROVIDERS, value);
        return this;
    }

    public AddKeyManagerWizard credentialReferenceStore(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(CREDENTIAL_REFERENCE_STORE, value);
        return this;
    }

    public AddKeyManagerWizard credentialReferenceAlias(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(CREDENTIAL_REFERENCE_ALIAS, value);
        return this;
    }

    public AddKeyManagerWizard credentialReferenceType(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(CREDENTIAL_REFERENCE_TYPE, value);
        return this;
    }

    public AddKeyManagerWizard credentialReferenceClearText(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(CREDENTIAL_REFERENCE_CLEAR_TEXT, value);
        return this;
    }
}
