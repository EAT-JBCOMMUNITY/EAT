package org.jboss.hal.testsuite.fragment.config.elytron.factory;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddCustomCredentialSecurityWizard extends WizardWindow {
    private static final String NAME = "name";
    private static final String CLASS_NAME = "class-name";
    private static final String MODULE = "module";

    public AddCustomCredentialSecurityWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddCustomCredentialSecurityWizard className(String value) {
        getEditor().text(CLASS_NAME, value);
        return this;
    }

    public AddCustomCredentialSecurityWizard module(String value) {
        getEditor().text(MODULE, value);
        return this;
    }
}
