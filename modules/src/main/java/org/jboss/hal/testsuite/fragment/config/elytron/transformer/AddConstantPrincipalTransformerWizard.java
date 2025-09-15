package org.jboss.hal.testsuite.fragment.config.elytron.transformer;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddConstantPrincipalTransformerWizard extends WizardWindow {

    private static final String NAME = "name";
    private static final String CONSTANT = "constant";

    public AddConstantPrincipalTransformerWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddConstantPrincipalTransformerWizard constant(String value) {
        getEditor().text(CONSTANT, value);
        return this;
    }
}
