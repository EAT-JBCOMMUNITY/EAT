package org.jboss.hal.testsuite.fragment.config.container;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class EJB3ThreadPoolWizard extends WizardWindow {

    public EJB3ThreadPoolWizard name(String value) {
        getEditor().text("name", value);
        return this;
    }

    public EJB3ThreadPoolWizard maxThreads(String value) {
        getEditor().text("max-threads", value);
        return this;
    }
}
