package org.jboss.hal.testsuite.fragment.config.undertow;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public abstract class AbstractAddHTTPListenerWizard<T extends AbstractAddHTTPListenerWizard> extends WizardWindow {
    private static final String NAME = "name";
    private static final String SOCKET_BINDING = "socket-binding";

    public T name(String value) {
        getEditor().text(NAME, value);
        return (T) this;
    }

    public T socketBinding(String value) {
        getEditor().text(SOCKET_BINDING, value);
        return (T) this;
    }
}
