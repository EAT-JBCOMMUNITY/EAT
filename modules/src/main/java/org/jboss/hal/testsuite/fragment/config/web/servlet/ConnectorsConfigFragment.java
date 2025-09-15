package org.jboss.hal.testsuite.fragment.config.web.servlet;

import org.jboss.hal.testsuite.fragment.ConfigFragment;

/**
 * Created by mkrajcov on 3/26/15.
 */
public class ConnectorsConfigFragment extends ConfigFragment {

    public ConnectorCreateWindow addConnector() {
        return getResourceManager().addResource(ConnectorCreateWindow.class);
    }

    public void removeConnector(String name) {
        getResourceManager().removeResourceAndConfirm(name);
    }

    public boolean isPresent(String connectorName) {
        return getResourceManager().selectByName(connectorName) != null;
    }
}
