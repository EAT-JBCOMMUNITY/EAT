package org.jboss.hal.testsuite.fragment.config.iiop;

import org.jboss.hal.testsuite.fragment.ConfigAreaFragment;
import org.jboss.hal.testsuite.fragment.config.resourceadapters.ConfigPropertiesFragment;

/**
 * Created by pcyprian on 12.8.15.
 */
public class IIOPConfigArea extends ConfigAreaFragment {

    public ConfigPropertiesFragment propertiesConfig() {
        return switchTo("Properties", ConfigPropertiesFragment.class);
    }
}
