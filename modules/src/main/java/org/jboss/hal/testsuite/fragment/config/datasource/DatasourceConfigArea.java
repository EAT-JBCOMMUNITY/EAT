package org.jboss.hal.testsuite.fragment.config.datasource;

import org.jboss.hal.testsuite.fragment.ConfigAreaFragment;
import org.jboss.hal.testsuite.util.PropUtils;

/**
 * @author jcechace
 */
public class DatasourceConfigArea extends ConfigAreaFragment {
    public ConnectionConfig connectionConfig() {
        String label = PropUtils.get("config.datasources.configarea.connection.tab.label");
        return switchTo(label, ConnectionConfig.class);
    }
}
