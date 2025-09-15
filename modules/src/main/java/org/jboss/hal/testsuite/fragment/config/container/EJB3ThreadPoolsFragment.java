package org.jboss.hal.testsuite.fragment.config.container;

import org.jboss.hal.testsuite.fragment.ConfigFragment;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class EJB3ThreadPoolsFragment extends ConfigFragment {

    public EJB3ThreadPoolWizard addThreadPool() {
        return getResourceManager().addResource(EJB3ThreadPoolWizard.class);
    }

    public void removeThreadPool(String name) {
        getResourceManager().removeResourceAndConfirm(name);
    }
}
