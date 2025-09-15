package org.jboss.hal.testsuite.fragment.config.elytron.other.ssl;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindowWithOptionalFields;

public class AddLDAPKeyStoreWizard extends WizardWindowWithOptionalFields {

    private static final String NAME = "name";
    private static final String DIR_CONTEXT = "dir-context";
    private static final String SEARCH_PATH = "search-path";
    private static final String ALIAS_ATTRIBUTE = "alias-attribute";
    private static final String CERTIFICATE_ATTRIBUTE = "certificate-attribute";
    private static final String CERTIFICATE_CHAIN_ATTRIBUTE = "certificate-chain-attribute";
    private static final String CERTIFICATE_CHAIN_ENCODING = "certificate-chain-encoding";
    private static final String CERTIFICATE_TYPE = "certificate-type";
    private static final String KEY_ATTRIBUTE = "key-attribute";
    private static final String KEY_TYPE = "key-type";
    private static final String FILTER_ALIAS = "filter-alias";
    private static final String FILTER_CERTIFICATE = "filter-certificate";
    private static final String FILTER_ITERATE = "filter-iterate";
    private static final String SEARCH_RECURSIVE = "search-recursive";
    private static final String SEARCH_TIME_LIMIT = "search-time-limit";


    public AddLDAPKeyStoreWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddLDAPKeyStoreWizard dirContext(String value) {
        getEditor().text(DIR_CONTEXT, value);
        return this;
    }

    public AddLDAPKeyStoreWizard searchPath(String value) {
        getEditor().text(SEARCH_PATH, value);
        return this;
    }

    public AddLDAPKeyStoreWizard aliasAttribute(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(ALIAS_ATTRIBUTE, value);
        return this;
    }

    public AddLDAPKeyStoreWizard certificateAttribute(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(CERTIFICATE_ATTRIBUTE, value);
        return this;
    }

    public AddLDAPKeyStoreWizard certificateChainAttribute(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(CERTIFICATE_CHAIN_ATTRIBUTE, value);
        return this;
    }

    public AddLDAPKeyStoreWizard certificateChainEncoding(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(CERTIFICATE_CHAIN_ENCODING, value);
        return this;
    }

    public AddLDAPKeyStoreWizard certificateType(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(CERTIFICATE_TYPE, value);
        return this;
    }

    public AddLDAPKeyStoreWizard keyAttribute(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(KEY_ATTRIBUTE, value);
        return this;
    }

    public AddLDAPKeyStoreWizard keyType(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(KEY_TYPE, value);
        return this;
    }

    public AddLDAPKeyStoreWizard filterAlias(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(FILTER_ALIAS, value);
        return this;
    }

    public AddLDAPKeyStoreWizard filterCertificate(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(FILTER_CERTIFICATE, value);
        return this;
    }

    public AddLDAPKeyStoreWizard filterIterate(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(FILTER_ITERATE, value);
        return this;
    }

    public AddLDAPKeyStoreWizard searchRecursive(boolean value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().checkbox(SEARCH_RECURSIVE, value);
        return this;
    }

    public AddLDAPKeyStoreWizard searchTimeLimit(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(SEARCH_TIME_LIMIT, value);
        return this;
    }
}
