package org.jboss.hal.testsuite.fragment.config.socketbindings;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class InboundSocketBindingWizard extends WizardWindow {
    private static final String NAME = "name";
    private static final String PORT = "port";
    private static final String GROUP = "group";

    public InboundSocketBindingWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public InboundSocketBindingWizard port(String value) {
        getEditor().text(PORT, value);
        return this;
    }

    public InboundSocketBindingWizard group(String value) {
        getEditor().select(GROUP, value);
        return this;
    }
}
