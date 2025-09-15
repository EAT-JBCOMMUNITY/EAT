package org.jboss.hal.testsuite.fragment.config.web.servlet;

import org.jboss.hal.testsuite.fragment.ConfigFragment;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class VirtualServersFragment extends ConfigFragment {
    public VirtualServersWizard addVirtualServer() {
        return getResourceManager().addResource(VirtualServersWizard.class);
    }

    public void removeVirtualServer(String name) {
        getResourceManager().removeResourceAndConfirm(name);
    }
}
