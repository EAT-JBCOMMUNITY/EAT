package org.jboss.hal.testsuite.fragment.config.messaging;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddDiscoveryGroupWizard extends WizardWindow {

    private static final String NAME = "name";
    private static final String INITIAL_WAIT_TIMEOUT = "initial-wait-timeout";
    private static final String JGROUPS_CHANNEL = "jgroups-channel";
    private static final String JGROUPS_CLUSTER = "jgroups-cluster";
    private static final String REFRESH_TIMEOUT = "refresh-timeout";
    private static final String SOCKET_BINDING = "socket-binding";

    public AddDiscoveryGroupWizard name(String name) {
        getEditor().text(NAME, name);
        return this;
    }

    public AddDiscoveryGroupWizard initialWaitTimeout(long initialWaitTimeout) {
        return initialWaitTimeout(String.valueOf(initialWaitTimeout));
    }

    public AddDiscoveryGroupWizard initialWaitTimeout(String initialWaitTimeout) {
        getEditor().text(INITIAL_WAIT_TIMEOUT, initialWaitTimeout);
        return this;
    }

    public AddDiscoveryGroupWizard jgroupsChannel(String jgroupsChannel) {
        getEditor().text(JGROUPS_CHANNEL, jgroupsChannel);
        return this;
    }

    public AddDiscoveryGroupWizard jgroupsCluster(String jgroupsCluster) {
        getEditor().text(JGROUPS_CLUSTER, jgroupsCluster);
        return this;
    }

    public AddDiscoveryGroupWizard refreshTimeout(String refreshTimeout) {
        getEditor().text(REFRESH_TIMEOUT, refreshTimeout);
        return this;
    }

    public AddDiscoveryGroupWizard refreshTimeout(long refreshTimeout) {
        return refreshTimeout(String.valueOf(refreshTimeout));
    }

    public AddDiscoveryGroupWizard socketBinding(String socketBinding) {
        getEditor().text(SOCKET_BINDING, socketBinding);
        return this;
    }

}
