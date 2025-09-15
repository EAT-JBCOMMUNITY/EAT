package org.jboss.hal.testsuite.fragment.config.messaging;

import org.jboss.hal.testsuite.fragment.WindowFragment;

public class AddMessagingProviderWindow extends WindowFragment {

    public AddMessagingProviderWindow name(String name) {
        getEditor().text("name", name);
        return this;
    }

    public AddMessagingProviderWindow clusterUser(String clusterUser) {
        getEditor().text("cluster-user", clusterUser);
        return this;
    }

    public AddMessagingProviderWindow clusterPassword(String clusterPassword) {
        getEditor().text("cluster-password", clusterPassword);
        return this;
    }

    public AddMessagingProviderWindow securityEnabled(boolean securityEnabled) {
        getEditor().checkbox("security-enabled", securityEnabled);
        return this;
    }

}
