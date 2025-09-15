package org.jboss.hal.testsuite.fragment.config.remoting;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class LocalOutboundConnectionWizard extends WizardWindow {

    public LocalOutboundConnectionWizard name(String name) {
        getEditor().text("name", name);
        return this;
    }

    public LocalOutboundConnectionWizard outboundSocketBindingRef(String socketBindingRef) {
        getEditor().text("outbound-socket-binding-ref", socketBindingRef);
        return this;
    }

}
