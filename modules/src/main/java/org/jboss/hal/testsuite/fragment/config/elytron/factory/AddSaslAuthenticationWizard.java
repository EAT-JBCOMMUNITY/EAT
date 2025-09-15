package org.jboss.hal.testsuite.fragment.config.elytron.factory;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddSaslAuthenticationWizard extends WizardWindow {
    private static final String NAME = "name";
    private static final String SASL_SERVER_FACTORY = "sasl-server-factory";
    private static final String SECURITY_DOMAIN = "security-domain";

    public AddSaslAuthenticationWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddSaslAuthenticationWizard saslServerFactory(String value) {
        getEditor().text(SASL_SERVER_FACTORY, value);
        return this;
    }

    public AddSaslAuthenticationWizard securityDomain(String value) {
        getEditor().text(SECURITY_DOMAIN, value);
        return this;
    }
}
