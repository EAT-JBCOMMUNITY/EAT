package org.jboss.hal.testsuite.fragment.config.elytron.factory;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddProviderServerWizard extends WizardWindow {
    private static final String NAME = "name";
    private static final String PROVIDERS = "providers";

    public AddProviderServerWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddProviderServerWizard providers(String value) {
        getEditor().text(PROVIDERS, value);
        return this;
    }
}
