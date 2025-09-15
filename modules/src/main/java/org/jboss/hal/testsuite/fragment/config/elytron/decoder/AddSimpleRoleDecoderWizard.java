package org.jboss.hal.testsuite.fragment.config.elytron.decoder;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddSimpleRoleDecoderWizard extends WizardWindow {
    private static final String NAME = "name";
    private static final String ATTRIBUTE = "attribute";

    public AddSimpleRoleDecoderWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddSimpleRoleDecoderWizard attribute(String value) {
        getEditor().text(ATTRIBUTE, value);
        return this;
    }
}
