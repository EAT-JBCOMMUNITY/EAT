package org.jboss.hal.testsuite.fragment.config.io;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

/**
 * Abstraction over add wizard for buffer pool
 */
public class BufferPoolWizard extends WizardWindow {

    public BufferPoolWizard name(String name) {
        getEditor().text("name", name);
        return this;
    }

}
