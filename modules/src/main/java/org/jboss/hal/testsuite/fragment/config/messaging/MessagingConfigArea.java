package org.jboss.hal.testsuite.fragment.config.messaging;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.hal.testsuite.fragment.ConfigAreaFragment;
import org.jboss.hal.testsuite.fragment.config.resourceadapters.ConfigPropertiesFragment;
import org.jboss.hal.testsuite.fragment.shared.table.ResourceTableFragment;
import org.jboss.hal.testsuite.fragment.shared.util.ResourceManager;

/**
 * Created by pcyprian on 3.9.15.
 */
public class MessagingConfigArea extends ConfigAreaFragment {

    public ConfigPropertiesFragment propertiesConfig() {
        return switchTo("Properties", ConfigPropertiesFragment.class);
    }

    public ConfigPropertiesFragment topicsConfig() {
        return switchTo("Topics", ConfigPropertiesFragment.class);
    }

    public ResourceTableFragment getResourceTable() {
        return Graphene.createPageFragment(ResourceManager.class, root).getResourceTable();
    }
}
