package org.jboss.hal.testsuite.page.config;

import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.fragment.config.remoting.HttpConnectorWizard;
import org.jboss.hal.testsuite.fragment.config.remoting.LocalOutboundConnectionWizard;
import org.jboss.hal.testsuite.fragment.config.remoting.NativeConnectorWizard;
import org.jboss.hal.testsuite.fragment.config.remoting.OutboundConnectionWizard;
import org.jboss.hal.testsuite.fragment.config.remoting.RemoteOutboundConnectionWizard;
import org.jboss.hal.testsuite.page.ConfigPage;
import org.jboss.hal.testsuite.util.Console;

public class RemotingSubsystemPage extends ConfigPage {

    public void navigate() {
        getSubsystemNavigation("Remoting")
                .selectRow()
                .invoke(FinderNames.VIEW);
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
        Application.waitUntilVisible();
    }

    public void switchToRemoteConnectorsTab() {
        switchTab("Remote Connectors");
    }

    public void switchToOutboundConnectionsTab() {
        switchTab("Outbound Connections");
    }

    public void switchToEndpointSecurity() {
        getConfig().clickTabByLabel("Security");
    }

    public void switchToEndpointChannels() {
        getConfig().clickTabByLabel("Channels");
    }

    public NativeConnectorWizard addNativeConnector() {
        return getResourceManager().addResource(NativeConnectorWizard.class);
    }

    public HttpConnectorWizard addHttpConnector() {
        return getResourceManager().addResource(HttpConnectorWizard.class);
    }

    public LocalOutboundConnectionWizard addLocalOutboundConnection() {
        return getResourceManager().addResource(LocalOutboundConnectionWizard.class);
    }

    public RemoteOutboundConnectionWizard addRemoteOutboundConnection() {
        return getResourceManager().addResource(RemoteOutboundConnectionWizard.class);
    }

    public OutboundConnectionWizard addOutboundConnection() {
        return getResourceManager().addResource(OutboundConnectionWizard.class);
    }

}
