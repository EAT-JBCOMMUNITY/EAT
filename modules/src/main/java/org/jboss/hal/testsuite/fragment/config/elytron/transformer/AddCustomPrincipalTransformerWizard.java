package org.jboss.hal.testsuite.fragment.config.elytron.transformer;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddCustomPrincipalTransformerWizard extends WizardWindow {

    private static final String NAME = "name";
    private static final String CLASS_NAME = "class-name";
    private static final String MODULE = "module";

    public AddCustomPrincipalTransformerWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddCustomPrincipalTransformerWizard className(String value) {
        getEditor().text(CLASS_NAME, value);
        return this;
    }

    public AddCustomPrincipalTransformerWizard module(String value) {
        getEditor().text(MODULE, value);
        return this;
    }
}
