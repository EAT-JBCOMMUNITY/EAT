package org.jboss.hal.testsuite.fragment.config.mail;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class MailServerWizard extends WizardWindow {

    private static final String SOCKET_BINDING = "socketBinding";
    private static final String TYPE = "type";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String SSL = "ssl";

    public MailServerWizard socketBinding(String value) {
        getEditor().text(SOCKET_BINDING, value);
        return this;
    }
    public MailServerWizard type(String value) {
        getEditor().select(TYPE, value);
        return this;
    }
    public MailServerWizard username(String value) {
        getEditor().text(USERNAME, value);
        return this;
    }
    public MailServerWizard password(String value) {
        getEditor().password(PASSWORD, value);
        return this;
    }
    public MailServerWizard ssl(boolean value) {
        getEditor().checkbox(SSL, value);
        return this;
    }
}
