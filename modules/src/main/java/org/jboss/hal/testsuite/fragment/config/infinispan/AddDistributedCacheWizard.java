package org.jboss.hal.testsuite.fragment.config.infinispan;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddDistributedCacheWizard extends WizardWindow {

    private static final String NAME = "name";
    private static final String CAPACITY_FACTOR = "capacity-factor";
    private static final String CONSISTENT_HASH_STRATEGY = "consistent-hash-strategy";
    private static final String JNDI_NAME = "jndi-name";
    private static final String L1_LIFESPAN = "l1-lifespan";
    private static final String MODE = "mode";
    private static final String MODULE = "module";
    private static final String OWNERS = "owners";
    private static final String REMOTE_TIMEOUT = "remote-timeout";
    private static final String SEGMENTS = "segments";
    private static final String STATISTICS_ENABLED = "statistics-enabled";


    public AddDistributedCacheWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddDistributedCacheWizard capacityFactor(String value) {
        getEditor().text(CAPACITY_FACTOR, value);
        return this;
    }

    public AddDistributedCacheWizard consistentHashStrategy(String value) {
        getEditor().select(CONSISTENT_HASH_STRATEGY, value);
        return this;
    }

    public AddDistributedCacheWizard jndiName(String value) {
        getEditor().text(JNDI_NAME, value);
        return this;
    }

    public AddDistributedCacheWizard l1Lifespan(String value) {
        getEditor().text(L1_LIFESPAN, value);
        return this;
    }

    public AddDistributedCacheWizard mode(String value) {
        getEditor().select(MODE, value);
        return this;
    }

    public AddDistributedCacheWizard module(String value) {
        getEditor().text(MODULE, value);
        return this;
    }

    public AddDistributedCacheWizard owners(String value) {
        getEditor().text(OWNERS, value);
        return this;
    }

    public AddDistributedCacheWizard remoteTimeout(String value) {
        getEditor().text(REMOTE_TIMEOUT, value);
        return this;
    }

    public AddDistributedCacheWizard segments(String value) {
        getEditor().text(SEGMENTS, value);
        return this;
    }

    public AddDistributedCacheWizard statisticsEnabled(boolean value) {
        getEditor().checkbox(STATISTICS_ENABLED, value);
        return this;
    }
}
