package org.jboss.hal.testsuite.fragment.config.elytron.factory;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddHttpAuthenticationWizard extends WizardWindow {
    private static final String NAME = "name";
    private static final String HTTP_SERVER_MECHANISM_FACTORY = "http-server-mechanism-factory";
    private static final String SECURITY_DOMAIN = "security-domain";

    public AddHttpAuthenticationWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddHttpAuthenticationWizard httpServerMechanismFactory(String value) {
        getEditor().text(HTTP_SERVER_MECHANISM_FACTORY, value);
        return this;
    }

    public AddHttpAuthenticationWizard securityDomain(String value) {
        getEditor().text(SECURITY_DOMAIN, value);
        return this;
    }
}
