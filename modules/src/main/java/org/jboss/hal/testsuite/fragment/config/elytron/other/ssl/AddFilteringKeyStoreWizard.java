package org.jboss.hal.testsuite.fragment.config.elytron.other.ssl;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddFilteringKeyStoreWizard extends WizardWindow {
    private static final String NAME = "name";
    private static final String ALIAS_FILTER = "alias-filter";
    private static final String KEY_STORE = "key-store";

    public AddFilteringKeyStoreWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddFilteringKeyStoreWizard aliasFilter(String value) {
        getEditor().text(ALIAS_FILTER, value);
        return this;
    }

    public AddFilteringKeyStoreWizard keyStore(String value) {
        getEditor().text(KEY_STORE, value);
        return this;
    }
}
