package org.jboss.hal.testsuite.fragment.config.resourceadapters;

import org.jboss.hal.testsuite.fragment.ConfigFragment;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class ConnectionDefinitionsFragment extends ConfigFragment {
    public ConnectionDefinitionWizard addConnectionDefinition() {
        return getResourceManager().addResource(ConnectionDefinitionWizard.class);
    }

    public void removeConnectionDefinition(String connectionDefinitionName) {
        getResourceManager().removeResourceAndConfirm(connectionDefinitionName);
    }
}
