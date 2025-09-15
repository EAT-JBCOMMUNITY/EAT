package org.jboss.hal.testsuite.fragment.config.federation;

import org.jboss.hal.testsuite.fragment.ConfigAreaFragment;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.util.PropUtils;

public class KeyStoreConfigArea extends ConfigAreaFragment {

    public ConfigFragment hostkeys() {
        String label = PropUtils.get("config.federation.ks.configarea.hostkeys.tab.label");
        return switchTo(label);
    }
}
