package org.jboss.hal.testsuite.fragment.config.elytron.other.ssl;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddServerSSLContextWizard extends WizardWindow {
    private static final String NAME = "name";
    private static final String KEY_MANAGER = "key-manager";

    public AddServerSSLContextWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddServerSSLContextWizard keyManager(String value) {
        getEditor().text(KEY_MANAGER, value);
        return this;
    }
}
