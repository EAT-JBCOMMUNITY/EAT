package org.jboss.hal.testsuite.fragment.config.undertow;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddApplicationSecurityDomainWizard extends WizardWindow {

    public AddApplicationSecurityDomainWizard name(String name) {
        getEditor().text("name", name);
        return this;
    }

    public AddApplicationSecurityDomainWizard httpAuthenticationFactory(String factory) {
        getEditor().text("http-authentication-factory", factory);
        return this;
    }

}
