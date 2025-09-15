package org.jboss.hal.testsuite.fragment.config;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindowWithOptionalFields;

public class AddResourceWizard extends WizardWindowWithOptionalFields {

    public AddResourceWizard name(String name) {
        getEditor().text("name", name);
        return this;
    }

    public AddResourceWizard text(String identifier, String value) {
        getEditor().text(identifier, value);
        return this;
    }

    public AddResourceWizard select(String identifier, String value) {
        getEditor().select(identifier, value);
        return this;
    }
}
