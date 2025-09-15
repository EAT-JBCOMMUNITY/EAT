package org.jboss.hal.testsuite.fragment.runtime.elytron;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindowWithOptionalFields;

public class AliasWizard extends WizardWindowWithOptionalFields {
    private static final String ALIAS = "alias";
    private static final String SECRET_VALUE = "secret-value";
    private static final String ENTRY_TYPE = "entry-type";

    public AliasWizard alias(String value) {
        getEditor().text(ALIAS, value);
        return this;
    }

    public AliasWizard secretValue(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(SECRET_VALUE, value);
        return this;
    }

    public AliasWizard entryType(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().select(ENTRY_TYPE, value);
        return this;
    }
}
