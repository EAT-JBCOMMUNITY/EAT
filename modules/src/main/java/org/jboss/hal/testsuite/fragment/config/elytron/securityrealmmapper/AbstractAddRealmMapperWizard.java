package org.jboss.hal.testsuite.fragment.config.elytron.securityrealmmapper;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public abstract class AbstractAddRealmMapperWizard<T extends  AbstractAddRealmMapperWizard> extends WizardWindow {

    public T name(String name) {
        getEditor().text("name", name);
        return (T) this;
    }

}
