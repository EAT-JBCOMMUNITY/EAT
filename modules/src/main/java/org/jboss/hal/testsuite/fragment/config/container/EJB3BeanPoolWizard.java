package org.jboss.hal.testsuite.fragment.config.container;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class EJB3BeanPoolWizard extends WizardWindow {

    public EJB3BeanPoolWizard name(String value) {
        getEditor().text("name", value);
        return this;
    }

    public EJB3BeanPoolWizard maxPoolSize(String value) {
        getEditor().text("max-pool-size", value);
        return this;
    }

    public EJB3BeanPoolWizard timeout(String value) {
        getEditor().text("timeout", value);
        return this;
    }

    public EJB3BeanPoolWizard timeoutUnit(String value) {
        getEditor().select("timeout-unit", value);
        return this;
    }

}
