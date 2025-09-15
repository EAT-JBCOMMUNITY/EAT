package org.jboss.hal.testsuite.fragment.config.elytron.other.ssl;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddAggregateProvidersWizard extends WizardWindow {

    private static final String NAME = "name";
    private static final String PROVIDERS = "providers";

    public AddAggregateProvidersWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddAggregateProvidersWizard providers(String value) {
        getEditor().text(PROVIDERS, value);
        return this;
    }
}
