package org.jboss.hal.testsuite.fragment.config.jgroups;

import org.jboss.hal.testsuite.fragment.ConfigAreaFragment;
import org.jboss.hal.testsuite.fragment.config.resourceadapters.ConfigPropertiesFragment;
/**
 * Created by jkasik on 5.8.15.
 */
public class JGroupsConfigArea extends ConfigAreaFragment {

    public ConfigPropertiesFragment propertiesConfig() {
        return switchTo("Properties", ConfigPropertiesFragment.class);
    }

    public JGroupsProtocolPropertiesFragment protocolPropertiesConfig() {
        return switchTo("Properties", JGroupsProtocolPropertiesFragment.class);
    }

    public JGroupsTransportPropertiesFragment transportPropertiesConfig() {
        return switchTo("Properties", JGroupsTransportPropertiesFragment.class);
    }

}
