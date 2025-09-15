package org.jboss.hal.testsuite.fragment.config.resourceadapters;

import org.jboss.hal.testsuite.fragment.ConfigAreaFragment;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class ResourceAdaptersConfigArea extends ConfigAreaFragment {

    private static final String PROPERTIES_LABEL = "Properties";

    public ConfigPropertiesFragment switchToProperty() {
        return switchTo(PROPERTIES_LABEL, ConfigPropertiesFragment.class);
    }
}
