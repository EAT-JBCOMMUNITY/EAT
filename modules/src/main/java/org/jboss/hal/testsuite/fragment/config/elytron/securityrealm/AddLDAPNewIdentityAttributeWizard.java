package org.jboss.hal.testsuite.fragment.config.elytron.securityrealm;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddLDAPNewIdentityAttributeWizard extends WizardWindow {
    private static final String NAME = "name";
    private static final String VALUE = "value";

    public AddLDAPNewIdentityAttributeWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddLDAPNewIdentityAttributeWizard value(String value) {
        getEditor().text(VALUE, value);
        return this;
    }
}
