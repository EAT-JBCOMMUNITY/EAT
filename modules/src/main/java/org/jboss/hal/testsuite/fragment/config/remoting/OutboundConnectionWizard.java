package org.jboss.hal.testsuite.fragment.config.remoting;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class OutboundConnectionWizard extends WizardWindow {

    public OutboundConnectionWizard name(String name) {
        getEditor().text("name", name);
        return this;
    }

    public OutboundConnectionWizard uri(String uri) {
        getEditor().text("uri", uri);
        return this;
    }
}
