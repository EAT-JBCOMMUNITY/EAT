package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.graphene.page.Location;
import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.page.ConfigPage;
import org.jboss.hal.testsuite.page.Navigatable;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
@Location("#modcluster")
public class ModClusterPage extends ConfigPage implements Navigatable {

    @Override
    public void navigate() {
        new FinderNavigation(browser, StandaloneConfigEntryPoint.class)
                .step(FinderNames.CONFIGURATION, FinderNames.SUBSYSTEMS)
                .step(FinderNames.SUBSYSTEM, "ModCluster")
                .selectRow().invoke(FinderNames.VIEW);
        Application.waitUntilVisible();
    }
}
