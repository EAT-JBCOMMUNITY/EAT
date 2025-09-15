package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.graphene.page.Location;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.page.ConfigPage;
import org.jboss.hal.testsuite.page.Navigatable;
import org.jboss.hal.testsuite.util.Console;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
@Location("#properties")
public class SystemPropertiesPage extends ConfigPage implements Navigatable {

    @Override
    public void navigate() {
        new FinderNavigation(browser, StandaloneConfigEntryPoint.class)
                .step(FinderNames.CONFIGURATION, "System Properties")
                .selectRow().invoke(FinderNames.VIEW);
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
    }
}
