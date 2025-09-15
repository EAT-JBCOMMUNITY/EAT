package org.jboss.hal.testsuite.fragment.config.iiop;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class IIOPPropertyWizard extends WizardWindow {

    private static final String KEY = "key";
    private static final String VALUE = "value";

    public IIOPPropertyWizard key(String value) {
        getEditor().text(KEY, value);
        return this;
    }

    public IIOPPropertyWizard value(String value) {
        getEditor().text(VALUE, value);
        return this;
    }
}
