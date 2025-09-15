package org.jboss.hal.testsuite.fragment.config.elytron.securityrealm;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindowWithOptionalFields;

public abstract class AbstractAddSecurityRealmWizard<T extends AbstractAddSecurityRealmWizard> extends WizardWindowWithOptionalFields {

    public T name(String name) {
        getEditor().text("name", name);
        return (T) this;
    }
}
