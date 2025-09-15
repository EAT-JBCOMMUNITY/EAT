package org.jboss.hal.testsuite.fragment.config.elytron.other;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class CustomPolicyWizard extends WizardWindow {
    private static final String NAME = "name";
    private static final String CLASS_NAME = "class-name";
    private static final String MODULE = "module";

    public CustomPolicyWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public CustomPolicyWizard className(String value) {
        getEditor().text(CLASS_NAME, value);
        return this;
    }

    public CustomPolicyWizard module(String value) {
        getEditor().text(MODULE, value);
        return this;
    }
}
