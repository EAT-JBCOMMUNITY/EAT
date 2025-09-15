package org.jboss.hal.testsuite.fragment.config.federation;

import org.jboss.hal.testsuite.fragment.ConfigAreaFragment;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.util.PropUtils;

/**
 * @author jcechace
 */
public class FederationConfigArea extends ConfigAreaFragment {
    public ConfigFragment samlConfig() {
        String label = PropUtils.get("config.federation.configarea.saml.tab.label");
        return switchTo(label, ConfigFragment.class);
    }
}
