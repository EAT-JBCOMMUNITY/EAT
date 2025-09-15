package org.jboss.hal.testsuite.fragment.config.infinispan;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddLocalCacheWizard extends WizardWindow {

    private static final String NAME = "name";
    private static final String JNDI_NAME = "jndi-name";
    private static final String MODULE = "module";
    private static final String STATISTICS_ENABLED = "statistics-enabled";

    public AddLocalCacheWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddLocalCacheWizard jndiName(String value) {
        getEditor().text(JNDI_NAME, value);
        return this;
    }

    public AddLocalCacheWizard module(String value) {
        getEditor().text(MODULE, value);
        return this;
    }

    public AddLocalCacheWizard statisticsEnabled(boolean value) {
        getEditor().checkbox(STATISTICS_ENABLED, value);
        return this;
    }
}
