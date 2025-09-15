package org.jboss.hal.testsuite.fragment.config.infinispan;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddReplicatedCacheWizard extends WizardWindow {
    private static final String NAME = "name";
    private static final String JNDI_NAME = "jndi-name";
    private static final String MODE = "mode";
    private static final String MODULE = "module";
    private static final String REMOTE_TIMEOUT = "remote-timeout";
    private static final String STATISTICS_ENABLED = "statistics-enabled";

    public AddReplicatedCacheWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddReplicatedCacheWizard jndiName(String value) {
        getEditor().text(JNDI_NAME, value);
        return this;
    }

    public AddReplicatedCacheWizard mode(String value) {
        getEditor().select(MODE, value);
        return this;
    }

    public AddReplicatedCacheWizard module(String value) {
        getEditor().text(MODULE, value);
        return this;
    }

    public AddReplicatedCacheWizard remoteTimeout(String value) {
        getEditor().text(REMOTE_TIMEOUT, value);
        return this;
    }

    public AddReplicatedCacheWizard statisticsEnabled(boolean value) {
        getEditor().checkbox(STATISTICS_ENABLED, value);
        return this;
    }
}
