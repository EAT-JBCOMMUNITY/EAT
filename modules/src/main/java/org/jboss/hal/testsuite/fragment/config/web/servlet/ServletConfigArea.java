package org.jboss.hal.testsuite.fragment.config.web.servlet;

import org.jboss.hal.testsuite.fragment.ConfigAreaFragment;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.util.PropUtils;

public class ServletConfigArea extends ConfigAreaFragment {

    public ConfigFragment global() {
        String label = PropUtils.get("config.web.servlet.configarea.global.tab.label");
        return switchTo(label);
    }

    public ConnectorsConfigFragment connectors() {
        String label = PropUtils.get("config.web.servlet.configarea.connectors.tab.label");
        return switchTo(label, ConnectorsConfigFragment.class);
    }

    public VirtualServersFragment virtualServers() {
        String label = PropUtils.get("config.web.servlet.configarea.virtualservers.tab.label");
        return switchTo(label, VirtualServersFragment.class);
    }

    public ConfigFragment jsp() {
        String label = PropUtils.get("config.web.servlet.configarea.jsp.tab.label");
        return switchTo(label);
    }
}
