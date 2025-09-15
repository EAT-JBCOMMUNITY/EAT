package org.jboss.hal.testsuite.fragment.config.resourceadapters;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class AdminObjectWizard extends WizardWindow {

    private static final String NAME = "name";
    private static final String JNDI_NAME = "jndi-name";
    private static final String CLASS_NAME = "class-name";

    public AdminObjectWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AdminObjectWizard jndiName(String value) {
        getEditor().text(JNDI_NAME, value);
        return this;
    }

    public AdminObjectWizard className(String value) {
        getEditor().text(CLASS_NAME, value);
        return this;
    }
}
