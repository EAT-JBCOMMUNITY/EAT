package org.jboss.hal.testsuite.fragment.config.jca;

import org.jboss.hal.testsuite.fragment.ConfigAreaFragment;
import org.jboss.hal.testsuite.fragment.config.resourceadapters.ConfigPropertiesFragment;

/**
 * Created by pcyprian on 24.8.15.
 */
public class JCAConfigArea extends ConfigAreaFragment {

    public ConfigPropertiesFragment poolConfig() {
        return switchTo("Pool", ConfigPropertiesFragment.class);
    }
}
