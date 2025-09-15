package org.jboss.hal.testsuite.fragment.config.remoting;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class NativeConnectorWizard extends WizardWindow {

    public NativeConnectorWizard name(String name) {
        getEditor().text("name", name);
        return this;
    }

    public NativeConnectorWizard socketBinding(String socketBinding) {
        getEditor().text("socket-binding", socketBinding);
        return this;
    }
}
