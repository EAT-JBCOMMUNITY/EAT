package org.jboss.hal.testsuite.fragment.config.remoting;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class RemoteOutboundConnectionWizard extends WizardWindow {

    public RemoteOutboundConnectionWizard name(String name) {
        getEditor().text("name", name);
        return this;
    }

    public RemoteOutboundConnectionWizard outboundSocketBindingRef(String socketBindingRef) {
        getEditor().text("outbound-socket-binding-ref", socketBindingRef);
        return this;
    }

}
