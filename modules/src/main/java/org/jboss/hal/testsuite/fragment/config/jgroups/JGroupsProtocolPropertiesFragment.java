package org.jboss.hal.testsuite.fragment.config.jgroups;

import org.jboss.hal.testsuite.fragment.config.resourceadapters.ConfigPropertiesFragment;
import org.jboss.hal.testsuite.fragment.shared.modal.ConfirmationWindow;

public class JGroupsProtocolPropertiesFragment extends ConfigPropertiesFragment {

    @Override
    public JGroupsProtocolPropertyWizard addProperty() {
        return getResourceManager().addResource(JGroupsProtocolPropertyWizard.class);
    }

    @Override
    public void removeProperty(String name) {
        ConfirmationWindow confirmationWindow = getResourceManager().removeResource(name);
        confirmationWindow.confirmAndDismissReloadRequiredMessage();
    }
}
