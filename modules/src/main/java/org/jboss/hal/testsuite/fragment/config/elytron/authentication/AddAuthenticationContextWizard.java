package org.jboss.hal.testsuite.fragment.config.elytron.authentication;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddAuthenticationContextWizard extends WizardWindow {

    public AddAuthenticationContextWizard name(String name) {
        getEditor().text("name", name);
        return this;
    }

    public AddAuthenticationContextWizard extendsContext(String extendsContext) {
        getEditor().text("extends", extendsContext);
        return this;
    }
}
