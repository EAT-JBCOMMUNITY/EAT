package org.jboss.hal.testsuite.fragment.config.infinispan;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class CacheContainerWizard extends WizardWindow {

    public CacheContainerWizard name(String value) {
        getEditor().text("name", value);
        return this;
    }

    public CacheContainerWizard aliases(String value) {
        getEditor().text("aliases", value);
        return this;
    }

    public CacheContainerWizard defaultCache(String value) {
        getEditor().text("default-cache", value);
        return this;
    }

    public CacheContainerWizard evictionExecutor(String value) {
        getEditor().text("eviction-executor", value);
        return this;
    }

    public CacheContainerWizard jndiName(String value) {
        getEditor().text("jndi-name", value);
        return this;
    }

    public CacheContainerWizard listenerExecutor(String value) {
        getEditor().text("listener-executor", value);
        return this;
    }

    public CacheContainerWizard module(String value) {
        getEditor().text("module", value);
        return this;
    }

    public CacheContainerWizard replicationQueueExecutor(String value) {
        getEditor().text("replication-queue-executor", value);
        return this;
    }

    public CacheContainerWizard start(String value) {
        getEditor().text("start", value);
        return this;
    }

    public CacheContainerWizard statisticsEnabled(boolean value) {
        getEditor().checkbox("statistics-enabled", value);
        return this;
    }

}
