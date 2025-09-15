package org.jboss.hal.testsuite.fragment.config.mail;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class MailSessionWizard extends WizardWindow {

    private static final String JNDI_NAME = "jndiName";
    private static final String NAME = "name";

    public MailSessionWizard jndiName(String value) {
        getEditor().text(JNDI_NAME, value);
        return this;
    }
    public MailSessionWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }
}
