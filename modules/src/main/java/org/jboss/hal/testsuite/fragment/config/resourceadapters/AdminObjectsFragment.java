package org.jboss.hal.testsuite.fragment.config.resourceadapters;

import org.jboss.hal.testsuite.fragment.ConfigFragment;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class AdminObjectsFragment extends ConfigFragment {
    public AdminObjectWizard addAdminObject() {
        return getResourceManager().addResource(AdminObjectWizard.class);
    }

    public void removeAdminObject(String adminObjectName) {
        getResourceManager().removeResourceAndConfirm(adminObjectName);
    }


}
