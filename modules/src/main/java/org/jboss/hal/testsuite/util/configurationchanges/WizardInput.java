package org.jboss.hal.testsuite.util.configurationchanges;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public interface WizardInput<T extends WizardWindow> {
    void fill(T window);
    void clear(T window);
    String getName();
}
