package org.jboss.hal.testsuite.fragment.config.interfaces;

import org.jboss.hal.testsuite.fragment.ConfigFragment;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class NetworkInterfaceContentFragment extends ConfigFragment {

    public NetworkInterfaceWizard addInterface() {
        return getResourceManager().addResource(NetworkInterfaceWizard.class);
    }

    public void removeInterface(String interfaceName) {
        getResourceManager().removeResourceAndConfirm(interfaceName);
    }

    public void selectInterface(String interfaceName) {
        getResourceManager().selectByName(interfaceName);
    }

}
