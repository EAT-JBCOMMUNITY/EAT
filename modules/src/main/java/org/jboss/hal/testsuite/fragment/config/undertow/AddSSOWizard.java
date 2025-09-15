package org.jboss.hal.testsuite.fragment.config.undertow;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindowWithOptionalFields;

public class AddSSOWizard extends WizardWindowWithOptionalFields {

    public AddSSOWizard keyAlias(String keyAlias) {
        getEditor().text("key-alias", keyAlias);
        return this;
    }

    public AddSSOWizard keyStore(String keyStore) {
        getEditor().text("key-store", keyStore);
        return this;
    }

    public AddSSOWizard clearText(String clearText) {
        maximizeWindow();
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text("clear-text", clearText);
        return this;
    }

}
