package org.jboss.hal.testsuite.fragment.config.elytron.factory;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddKerberosSecurityWizard extends WizardWindow {
    private static final String NAME = "name";
    private static final String PATH = "path";
    private static final String PRINCIPAL = "principal";

    public AddKerberosSecurityWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddKerberosSecurityWizard path(String value) {
        getEditor().text(PATH, value);
        return this;
    }

    public AddKerberosSecurityWizard principal(String value) {
        getEditor().text(PRINCIPAL, value);
        return this;
    }
}
