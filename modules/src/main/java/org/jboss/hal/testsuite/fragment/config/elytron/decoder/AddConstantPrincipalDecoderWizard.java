package org.jboss.hal.testsuite.fragment.config.elytron.decoder;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddConstantPrincipalDecoderWizard extends WizardWindow {
    private static final String NAME = "name";
    private static final String CONSTANT = "constant";

    public AddConstantPrincipalDecoderWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddConstantPrincipalDecoderWizard constant(String value) {
        getEditor().text(CONSTANT, value);
        return this;
    }
}
