package org.jboss.hal.testsuite.fragment.config.io;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

/**
 * Abstraction over add wizard for io worker
 */
public class WorkerWizard extends WizardWindow {

    public WorkerWizard name(String name) {
        getEditor().text("name", name);
        return this;
    }

}
