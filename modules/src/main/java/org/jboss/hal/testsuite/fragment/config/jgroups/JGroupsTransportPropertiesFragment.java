package org.jboss.hal.testsuite.fragment.config.jgroups;

import org.jboss.hal.testsuite.fragment.config.resourceadapters.ConfigPropertiesFragment;
import org.jboss.hal.testsuite.fragment.shared.modal.ConfirmationWindow;

public class JGroupsTransportPropertiesFragment extends ConfigPropertiesFragment {

    public JGroupsTransportPropertyWizard addProperty() {
        return getResourceManager().addResource(JGroupsTransportPropertyWizard.class);
    }

    @Override
    public void removeProperty(String name) {
        ConfirmationWindow confirmationWindow = getResourceManager().removeResource(name);
        confirmationWindow.confirmAndDismissReloadRequiredMessage();
    }
}
